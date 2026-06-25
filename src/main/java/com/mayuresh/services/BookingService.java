package com.mayuresh.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mayuresh.dto.BookingDTO;
import com.mayuresh.entities.Booking;
import com.mayuresh.entities.Route;
import com.mayuresh.entities.User;
import com.mayuresh.entities.Vehicle;
import com.mayuresh.repositiories.BookingRepository;
import com.mayuresh.repositiories.RouteRepository;
import com.mayuresh.repositiories.UserRepository;
import com.mayuresh.repositiories.VehicleRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	UniversalResponse response;
	
	// add new route
	public ResponseEntity<ResponseWrapper> addBooking(Booking booking)
	{
		Optional<User> existingUser=userRepository.findById(booking.getUser().getUserId());
		Optional<Route> existingRoute=routeRepository.findById(booking.getRoute().getRouteId());
		Optional<Vehicle> existingVehicle=vehicleRepository.findById(booking.getVehicle().getVehicleId());

		if(existingUser.isEmpty() || existingRoute.isEmpty() || existingVehicle.isEmpty())
		{
			return response.send("User, route, or vehicle not found", null, HttpStatus.NOT_FOUND);
		}

		booking.setUser(existingUser.get());
		booking.setRoute(existingRoute.get());
		booking.setVehicle(existingVehicle.get());

		if(existingVehicle.get().getTripDate()==null)
		{
			return response.send("Trip date is not added for this vehicle", null, HttpStatus.BAD_REQUEST);
		}

		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus("CONFIRMED");
		if(booking.getSeats()!=null && booking.getSeats().size()>0)
		{
			booking.setFare(existingVehicle.get().getFare()*booking.getSeats().size());
		}
		else
		{
			booking.setFare(existingVehicle.get().getFare());
		}
		booking.setTripDate(existingVehicle.get().getTripDate());
		booking.setCancellationCharge(0);
		booking.setRefundAmount(0);

		Booking savedBooking=bookingRepository.save(booking);
		return response.send("Booking created", convertToDTO(savedBooking), HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseWrapper> addUserBooking(Long userId, Long routeId, Long vehicleId, Booking booking)
	{
		User user=new User();
		user.setUserId(userId);

		Route route=new Route();
		route.setRouteId(routeId);

		Vehicle vehicle=new Vehicle();
		vehicle.setVehicleId(vehicleId);

		booking.setUser(user);
		booking.setRoute(route);
		booking.setVehicle(vehicle);

		return addBooking(booking);
	}
	
	// find/give all routes
	public ResponseEntity<ResponseWrapper> getAllBookings()
	{
		List<Booking> allBookings=bookingRepository.findAll();
		if(allBookings.size()>0)
		{
			return response.send("following Booking found", convertToDTOList(allBookings), HttpStatus.FOUND);
		}
		else
		{
			return response.send("no Booking found", null, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseWrapper> getBookingsByUser(Long userId)
	{
		List<Booking> userBookings=bookingRepository.findByUserUserId(userId);
		return response.send("Following user bookings found", convertToDTOList(userBookings), HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper> getBookedSeats(Long vehicleId)
	{
		List<Booking> bookings=bookingRepository.findByVehicleVehicleIdAndStatusNot(vehicleId, "CANCELLED");
		List<Integer> bookedSeats=bookings.stream()
				.filter(booking -> booking.getSeats()!=null)
				.flatMap(booking -> booking.getSeats().stream())
				.toList();

		return response.send("Following seats are booked", bookedSeats, HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper> cancelBooking(Long userId, Long bookingId)
	{
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);

		if(existingBooking.isEmpty())
		{
			return response.send("Booking not found", null, HttpStatus.NOT_FOUND);
		}

		Booking booking=existingBooking.get();

		if(booking.getUser().getUserId()!=userId.longValue())
		{
			return response.send("This booking does not belong to this user", null, HttpStatus.BAD_REQUEST);
		}

		if(booking.getStatus().equalsIgnoreCase("CANCELLED"))
		{
			return response.send("Booking already cancelled", convertToDTO(booking), HttpStatus.BAD_REQUEST);
		}

		if(booking.getTripDate()==null)
		{
			return response.send("Trip date not found for this booking", convertToDTO(booking), HttpStatus.BAD_REQUEST);
		}

		LocalDate lastCancelDate=booking.getTripDate().minusDays(2);
		if(LocalDate.now().isAfter(lastCancelDate))
		{
			return response.send("Booking can be cancelled only before 2 days of trip", convertToDTO(booking), HttpStatus.BAD_REQUEST);
		}

		double cancellationCharge=booking.getFare()*0.30;
		double refundAmount=booking.getFare()-cancellationCharge;

		booking.setStatus("CANCELLED");
		booking.setCancellationCharge(cancellationCharge);
		booking.setRefundAmount(refundAmount);

		Booking savedBooking=bookingRepository.save(booking);
		return response.send("Booking cancelled. 30% cancellation charge deducted", convertToDTO(savedBooking), HttpStatus.OK);
	}
	
	// get route by id
	public ResponseEntity<ResponseWrapper> getBookingById(Long bookingId)
	{
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);
		if(existingBooking.isPresent())
		{
			return response.send("Booking by id " + bookingId + "is found", convertToDTO(existingBooking.get()), HttpStatus.FOUND);
		}
		else
		{
			return response.send("no Booking found by id " + bookingId , null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	// delete route by id 
	public ResponseEntity<ResponseWrapper> deleteBookingById(Long bookingId)
	{
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);
		if(existingBooking.isPresent())
		{
			bookingRepository.deleteById(bookingId);
			return response.send("Booking by id " + bookingId + "is deleted", convertToDTO(existingBooking.get()), HttpStatus.OK);
		}
		else
		{
			return response.send("no Booking found by id" + bookingId , null, HttpStatus.NOT_FOUND);
		}
	}
	
	//update route by id
	public ResponseEntity<ResponseWrapper> updateBookingById(Long bookingId,Booking booking) 
	{
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);
		if(existingBooking.isPresent())
		{
			booking.setBookingId(bookingId);
			Booking savedBooking=bookingRepository.save(booking);
			return response.send("Booking by id " + bookingId + "is updated", convertToDTO(savedBooking), HttpStatus.OK);
		}
		else
		{
			return response.send("no Booking found by id" + bookingId , null, HttpStatus.NOT_FOUND);
		}
	}

	private List<BookingDTO> convertToDTOList(List<Booking> bookings)
	{
		return bookings.stream().map(this::convertToDTO).toList();
	}

	private BookingDTO convertToDTO(Booking booking)
	{
		BookingDTO bookingDTO=new BookingDTO();
		bookingDTO.setBookingId(booking.getBookingId());
		bookingDTO.setBookingTime(booking.getBookingTime());
		bookingDTO.setFare(booking.getFare());
		bookingDTO.setTripDate(booking.getTripDate());
		bookingDTO.setStatus(booking.getStatus());
		bookingDTO.setCancellationCharge(booking.getCancellationCharge());
		bookingDTO.setRefundAmount(booking.getRefundAmount());
		bookingDTO.setSeats(booking.getSeats());

		if(booking.getUser()!=null)
		{
			bookingDTO.setUserId(booking.getUser().getUserId());
			bookingDTO.setUserName(booking.getUser().getName());
			bookingDTO.setUserEmail(booking.getUser().getEmail());
		}

		if(booking.getRoute()!=null)
		{
			bookingDTO.setRouteId(booking.getRoute().getRouteId());
			bookingDTO.setRouteName(booking.getRoute().getRouteName());
			bookingDTO.setSource(booking.getRoute().getSource());
			bookingDTO.setDestination(booking.getRoute().getDestination());
		}

		if(booking.getVehicle()!=null)
		{
			bookingDTO.setVehicleId(booking.getVehicle().getVehicleId());
			bookingDTO.setVehicleName(booking.getVehicle().getVehicleName());
			bookingDTO.setVehicleType(booking.getVehicle().getVehicleType());
			if(bookingDTO.getSource()==null || bookingDTO.getSource().isBlank())
			{
				bookingDTO.setSource(booking.getVehicle().getSource());
			}
			if(bookingDTO.getDestination()==null || bookingDTO.getDestination().isBlank())
			{
				bookingDTO.setDestination(booking.getVehicle().getDestination());
			}
		}

		return bookingDTO;
	}

}

package com.mayuresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayuresh.entities.Booking;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.services.BookingService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	
	@GetMapping("/admin/bookings")
	public ResponseEntity<ResponseWrapper> getAllbookings()
	{
		return bookingService.getAllBookings();
	}
	
	@PostMapping("/admin/bookings")
	public ResponseEntity<ResponseWrapper> addbooking(@RequestBody Booking booking)
	{
		return bookingService.addBooking(booking);
	}

	@PostMapping("/bookings/{userId}/{routeId}/{vehicleId}")
	public ResponseEntity<ResponseWrapper> addUserBooking(@PathVariable Long userId, @PathVariable Long routeId, @PathVariable Long vehicleId, @RequestBody Booking booking)
	{
		return bookingService.addUserBooking(userId, routeId, vehicleId, booking);
	}

	@GetMapping("/bookings/booked-seats/{vehicleId}")
	public ResponseEntity<ResponseWrapper> getBookedSeats(@PathVariable Long vehicleId)
	{
		return bookingService.getBookedSeats(vehicleId);
	}
	
	@GetMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> getBookingById(@PathVariable Long bookingId)
	{
		return bookingService.getBookingById(bookingId);
	}
	
	@DeleteMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> deleteBookingById(@PathVariable Long bookingId)
	{
		return bookingService.deleteBookingById(bookingId);
	}
	
	@PutMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> updateBookingById(@PathVariable Long bookingId, @RequestBody Booking booking)
	{
		return bookingService.updateBookingById(bookingId, booking);
	}

	@GetMapping("/users/{userId}/bookings")
	public ResponseEntity<ResponseWrapper> getBookingsByUser(@PathVariable Long userId)
	{
		return bookingService.getBookingsByUser(userId);
	}

	@PutMapping("/users/{userId}/bookings/{bookingId}/cancel")
	public ResponseEntity<ResponseWrapper> cancelBooking(@PathVariable Long userId, @PathVariable Long bookingId)
	{
		return bookingService.cancelBooking(userId, bookingId);
	}

}

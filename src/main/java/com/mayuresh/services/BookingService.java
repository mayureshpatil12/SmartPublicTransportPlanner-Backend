package com.mayuresh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mayuresh.entities.Booking;
import com.mayuresh.repositiories.BookingRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	UniversalResponse response;
	
	// add new route
	public ResponseEntity<ResponseWrapper> addBooking(Booking booking)
	{
		Booking savedBooking=bookingRepository.save(booking);
		return response.send("Booking created", savedBooking, HttpStatus.CREATED);
	}
	
	// find/give all routes
	public ResponseEntity<ResponseWrapper> getAllBookings()
	{
		List<Booking> allBookings=bookingRepository.findAll();
		if(allBookings.size()>0)
		{
			return response.send("following Booking found", allBookings, HttpStatus.FOUND);
		}
		else
		{
			return response.send("no Booking found", null, HttpStatus.NOT_FOUND);
		}
	}
	
	// get route by id
	public ResponseEntity<ResponseWrapper> getBookingById(Long bookingId)
	{
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);
		if(existingBooking.isPresent())
		{
			return response.send("Booking by id " + bookingId + "is found", existingBooking, HttpStatus.FOUND);
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
			return response.send("Booking by id " + bookingId + "is deleted", existingBooking, HttpStatus.OK);
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
			return response.send("Booking by id " + bookingId + "is updated", savedBooking, HttpStatus.OK);
		}
		else
		{
			return response.send("no Booking found by id" + bookingId , null, HttpStatus.NOT_FOUND);
		}
	}

}

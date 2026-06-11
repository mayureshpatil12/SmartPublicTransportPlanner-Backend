package com.mayuresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> getBookingById(Long bookingId)
	{
		return bookingService.getBookingById(bookingId);
	}
	
	@DeleteMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> deleteBookingById(Long bookingId)
	{
		return bookingService.deleteBookingById(bookingId);
	}
	
	@PutMapping("/admin/bookings/{bookingId}")
	public ResponseEntity<ResponseWrapper> updateBookingById(Long bookingId, Booking booking)
	{
		return bookingService.updateBookingById(bookingId, booking);
	}

}

package com.mayuresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mayuresh.entities.ReportStatus;
import com.mayuresh.entities.UserReport;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.services.UserReportService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserReportController {

	@Autowired
	UserReportService userReportService;

	@PostMapping("/users/{userId}/bookings/{bookingId}/reports")
	public ResponseEntity<ResponseWrapper> addUserReport(@PathVariable Long userId, @PathVariable Long bookingId, @RequestBody UserReport userReport)
	{
		return userReportService.addUserReport(userId, bookingId, userReport);
	}

	@GetMapping("/users/{userId}/reports")
	public ResponseEntity<ResponseWrapper> getReportsByUser(@PathVariable Long userId)
	{
		return userReportService.getReportsByUser(userId);
	}

	@GetMapping("/admin/reports")
	public ResponseEntity<ResponseWrapper> getAllReports()
	{
		return userReportService.getAllReports();
	}

	@PutMapping("/admin/reports/{reportId}/status")
	public ResponseEntity<ResponseWrapper> updateReportStatus(@PathVariable Long reportId, @RequestParam ReportStatus status)
	{
		return userReportService.updateReportStatus(reportId, status);
	}
}

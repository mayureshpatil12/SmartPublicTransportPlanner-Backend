package com.mayuresh.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mayuresh.dto.UserReportDTO;
import com.mayuresh.entities.Booking;
import com.mayuresh.entities.ReportStatus;
import com.mayuresh.entities.Route;
import com.mayuresh.entities.User;
import com.mayuresh.entities.UserReport;
import com.mayuresh.repositiories.BookingRepository;
import com.mayuresh.repositiories.RouteRepository;
import com.mayuresh.repositiories.UserReportRepository;
import com.mayuresh.repositiories.UserRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;

@Service
public class UserReportService {

	@Autowired
	UserReportRepository userReportRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	UniversalResponse response;

	public ResponseEntity<ResponseWrapper> addUserReport(Long userId, Long bookingId, UserReport userReport)
	{
		Optional<User> existingUser=userRepository.findById(userId);
		Optional<Booking> existingBooking=bookingRepository.findById(bookingId);

		if(existingUser.isEmpty() || existingBooking.isEmpty())
		{
			return response.send("User or booking not found", null, HttpStatus.NOT_FOUND);
		}

		Booking booking=existingBooking.get();
		Optional<Route> existingRoute=routeRepository.findById(booking.getRoute().getRouteId());

		userReport.setUser(existingUser.get());
		userReport.setBooking(booking);
		if(existingRoute.isPresent())
		{
			userReport.setRoute(existingRoute.get());
		}
		userReport.setCreatedAt(LocalDateTime.now());
		userReport.setStatus(ReportStatus.PENDING);

		UserReport savedReport=userReportRepository.save(userReport);
		return response.send("Report submitted", convertToDTO(savedReport), HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseWrapper> getReportsByUser(Long userId)
	{
		List<UserReport> userReports=userReportRepository.findByUserUserId(userId);
		return response.send("Following user reports found", convertToDTOList(userReports), HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper> getAllReports()
	{
		List<UserReport> allReports=userReportRepository.findAll();
		return response.send("Following reports found", convertToDTOList(allReports), HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper> updateReportStatus(Long reportId, ReportStatus status)
	{
		Optional<UserReport> existingReport=userReportRepository.findById(reportId);
		if(existingReport.isEmpty())
		{
			return response.send("Report not found", null, HttpStatus.NOT_FOUND);
		}

		UserReport report=existingReport.get();
		report.setStatus(status);
		UserReport savedReport=userReportRepository.save(report);
		return response.send("Report status updated", convertToDTO(savedReport), HttpStatus.OK);
	}

	private List<UserReportDTO> convertToDTOList(List<UserReport> userReports)
	{
		return userReports.stream().map(this::convertToDTO).toList();
	}

	private UserReportDTO convertToDTO(UserReport userReport)
	{
		UserReportDTO userReportDTO=new UserReportDTO();
		userReportDTO.setReportId(userReport.getReportId());
		userReportDTO.setType(userReport.getType());
		userReportDTO.setDescription(userReport.getDescription());
		userReportDTO.setLatitude(userReport.getLatitude());
		userReportDTO.setLongitude(userReport.getLongitude());
		userReportDTO.setCreatedAt(userReport.getCreatedAt());
		userReportDTO.setStatus(userReport.getStatus());

		if(userReport.getUser()!=null)
		{
			userReportDTO.setUserId(userReport.getUser().getUserId());
			userReportDTO.setUserName(userReport.getUser().getName());
		}

		if(userReport.getBooking()!=null)
		{
			userReportDTO.setBookingId(userReport.getBooking().getBookingId());
		}

		if(userReport.getRoute()!=null)
		{
			userReportDTO.setRouteId(userReport.getRoute().getRouteId());
			userReportDTO.setSource(userReport.getRoute().getSource());
			userReportDTO.setDestination(userReport.getRoute().getDestination());
		}

		return userReportDTO;
	}
}

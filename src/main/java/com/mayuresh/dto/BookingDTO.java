package com.mayuresh.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BookingDTO {
	private Long bookingId;
	private LocalDateTime bookingTime;
	private double fare;
	private LocalDate tripDate;
	private String status;
	private double cancellationCharge;
	private double refundAmount;
	private List<Integer> seats;
	private Long userId;
	private String userName;
	private String userEmail;
	private Long routeId;
	private String routeName;
	private String source;
	private String destination;
	private Long vehicleId;
	private String vehicleName;
	private String vehicleType;
}

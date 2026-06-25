package com.mayuresh.dto;

import java.time.LocalDateTime;

import com.mayuresh.entities.ReportStatus;
import com.mayuresh.entities.ReportType;

import lombok.Data;

@Data
public class UserReportDTO {
	private Long reportId;
	private ReportType type;
	private String description;
	private double latitude;
	private double longitude;
	private LocalDateTime createdAt;
	private ReportStatus status;
	private Long userId;
	private String userName;
	private Long bookingId;
	private Long routeId;
	private String source;
	private String destination;
}

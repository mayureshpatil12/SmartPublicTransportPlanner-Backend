package com.mayuresh.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private String description;

    private double latitude;
    private double longitude;

    private LocalDateTime createdAt;

    // Many reports → One user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional: link to route
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;


    // Report lifecycle
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

}

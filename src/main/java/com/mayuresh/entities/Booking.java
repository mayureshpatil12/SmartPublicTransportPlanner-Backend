package com.mayuresh.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private double fare;

    // PENDING, CONFIRMED, CANCELLED, COMPLETED
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private long seat;

    // 🔗 Many Bookings -> One User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 🔗 Many Bookings -> One Route
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    // 🔗 Many Bookings -> One Vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
	
	
}

package com.mayuresh.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

    // when booking was made
    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private double fare;

    private LocalDate tripDate;

    // PENDING, CONFIRMED, CANCELLED, COMPLETED
    @Column(nullable = false)
    private String status;

    private double cancellationCharge;

    private double refundAmount;

    @ElementCollection
    private List<Integer> seats;

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

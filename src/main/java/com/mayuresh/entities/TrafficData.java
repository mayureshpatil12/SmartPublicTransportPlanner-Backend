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
public class TrafficData {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trafficId;

    // congestion level
    @Enumerated(EnumType.STRING)
    private TrafficLevel level;

    // location coordinates
    private double latitude;
    private double longitude;

    private LocalDateTime timestamp;

    // Optional: link to route
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

  
}

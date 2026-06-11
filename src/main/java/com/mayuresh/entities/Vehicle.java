package com.mayuresh.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Vehicle {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long vehicleId;

	    @Column(nullable = false, unique = true)
	    private String vehicleNumber;

	    @Column(nullable = false)
	    private String vehicleName;

	    // AC, NON_AC, SLEEPER, SEMI_SLEEPER, LUXURY
	    @Column(nullable = false)
	    private String vehicleType;

	    @Column(nullable = false)
	    private int capacity;

	    // ACTIVE, INACTIVE, MAINTENANCE
	    @Column(nullable = false)
	    private String status;

	    // 🔗 Many Vehicles -> One Route
	    @ManyToOne
	    @JoinColumn(name = "route_id", nullable = false)
	    
	    @JsonBackReference
	    private Route route;

	    // 🔗 One Vehicle -> Many Bookings
	    @OneToMany(mappedBy = "vehicle")
	    private List<Booking> bookings;
	    
	    
}

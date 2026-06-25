package com.mayuresh.entities;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Route {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long routeId;

	    @Column(nullable = false)
	    private String routeName;

	    @Column(nullable = false)
	    private String source;

	    @Column(nullable = false)
	    private String destination;

	    // departure time from source
	    @Column(nullable = false)
	    private LocalTime sourceTime;

	    // arrival time at destination
	    @Column(nullable = false)
	    private LocalTime destinationTime;

	    // example:
	    // "Thane,Dadar,Andheri,Bandra"
	    @Column(length = 1000)
	    private String intermediateStops;

	    private double distance;

	    // One Route -> Many Vehicles
	    @JsonManagedReference
	    @OneToMany(mappedBy = "route")
	    private List<Vehicle> vehicles;

	    // One Route -> Many Bookings
	    @JsonIgnore
	    @OneToMany(mappedBy = "route")
	    private List<Booking> bookings;


	    // One Route -> Many Reports
	    @JsonIgnore
	    @OneToMany(mappedBy = "route")
	    private List<UserReport> reports;

	    
	    
    
}

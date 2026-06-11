	package com.mayuresh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mayuresh.entities.Route;
import com.mayuresh.entities.Vehicle;
import com.mayuresh.repositiories.RouteRepository;
import com.mayuresh.repositiories.VehicleRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;

@Service
public class VehicleService {
	
	@Autowired
	 VehicleRepository vehicleRepository;
	
	@Autowired
	UniversalResponse response;
	
	@Autowired
	RouteRepository routeRepository;
	
	// add new Vehicle
	public ResponseEntity<ResponseWrapper> addVehicle(Vehicle vehicle)
	{
		Vehicle savedVehicle=vehicleRepository.save(vehicle);
		return response.send("Vehicle saved", savedVehicle, HttpStatus.CREATED);
	}
	
	// find/give all Vehicle
	public ResponseEntity<ResponseWrapper> getAllVehicle()
	{
		List<Vehicle> allVehicles=vehicleRepository.findAll();
		if(allVehicles.size()>0)
		{
			return response.send("following Vehicles found", allVehicles, HttpStatus.FOUND);
		}
		else
		{
			return response.send("no Vehicles found", null, HttpStatus.NOT_FOUND);
		}
	}
	
	// get Vehicle by id
	public ResponseEntity<ResponseWrapper> getVehicleById(Long VehicleId)
	{
		Optional<Vehicle> existingVehicle=vehicleRepository.findById(VehicleId);
		if(existingVehicle.isPresent())
		{
			return response.send("Vehicle by id " + VehicleId + "is found", existingVehicle, HttpStatus.FOUND);
		}
		else
		{
			return response.send("no Vehicle found by id " + VehicleId , null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	// delete Vehicle by id 
	public ResponseEntity<ResponseWrapper> deleteVehicleById(Long vehicleId)
	{
		Optional<Vehicle> existingVehicle=vehicleRepository.findById(vehicleId);
		if(existingVehicle.isPresent())
		{
			vehicleRepository.deleteById(vehicleId);
			return response.send("Vehicle by id " + vehicleId + "is deleted", existingVehicle, HttpStatus.OK);
		}
		else
		{
			return response.send("no Vehicle found by id" + vehicleId , null, HttpStatus.NOT_FOUND);
		}
	}
	
	//update Vehicle by id
	public ResponseEntity<ResponseWrapper> updateVehicleById(Long vehicleId, Vehicle vehicle) 
	{
		Optional<Vehicle> existingVehicle=vehicleRepository.findById(vehicleId);
		if(existingVehicle.isPresent())
		{
			vehicle.setVehicleId(vehicleId);
			Vehicle savedVehicle=vehicleRepository.save(vehicle);
			return response.send("Vehicle by id " + vehicleId + "is updated", savedVehicle, HttpStatus.OK);
		}
		else
		{
			return response.send("no Vehicle found by id" + vehicleId , null, HttpStatus.NOT_FOUND);
		}
	}
	
	
	public ResponseEntity<ResponseWrapper> assignVehicleToRoute(Long vehicleId, Long routeId)
	{
	    Optional<Vehicle> existingVehicle =
	            vehicleRepository.findById(vehicleId);

	    Optional<Route> existingRoute =
	            routeRepository.findById(routeId);

	    if(existingVehicle.isPresent())
	    {
	        if(existingRoute.isPresent())
	        {
	            Vehicle vehicle = existingVehicle.get();
	            vehicle.setRoute(existingRoute.get());
	            Vehicle updatedVehicle =vehicleRepository.save(vehicle);
	            return response.send("Vehicle assigned to route successfully",updatedVehicle,HttpStatus.OK);
	        }
	        else
	        {
	            return response.send("No route found with ID " + routeId,null,HttpStatus.NOT_FOUND);
	        }
	    }
	    else
	    {
	        return response.send("No vehicle found with ID " + vehicleId,null,HttpStatus.NOT_FOUND);
	    }
	}
	
	public ResponseEntity<ResponseWrapper>
	assignVehicleStatus(Long vehicleId, String status)
	{
	    Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicleId);

	    if(existingVehicle.isPresent())
	    {
	        Vehicle vehicle = existingVehicle.get();
	        vehicle.setStatus(status);
	        Vehicle updatedVehicle =vehicleRepository.save(vehicle);
	        return response.send("Vehicle status updated successfully",updatedVehicle,HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No vehicle found with ID " + vehicleId,null,HttpStatus.NOT_FOUND);
	    }
	}
	

	
	
	// extra bussiness logic 
//	
//	public ResponseEntity<ResponseWrapper> getVehiclesByType(String vehicleType)
//	{}
//	
//	public ResponseEntity<ResponseWrapper> getVehiclesByRoute(Long routeId)
//	{}
//	
//	public ResponseEntity<ResponseWrapper> getVehiclesBySourceAndDestination(String source, String destination)
//	{}
//	
//	public ResponseEntity<ResponseWrapper> changeVehicleStatus(Long vehicleId, String status)
//	{}
	
	
	
}

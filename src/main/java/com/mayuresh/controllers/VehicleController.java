package com.mayuresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayuresh.entities.Vehicle;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.services.VehicleService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class VehicleController{

    @Autowired
    VehicleService vehicleService;

    @GetMapping("/admin/vehicles")
    public ResponseEntity<ResponseWrapper> getAllVehicle()
    {
        return vehicleService.getAllVehicle();
    }

    @PostMapping("/admin/vehicles")
    public ResponseEntity<ResponseWrapper>
    addVehicle(@RequestBody Vehicle vehicle)
    {
        return vehicleService.addVehicle(vehicle);
    }

    @GetMapping("/admin/vehicles/{vehicleId}")
    public ResponseEntity<ResponseWrapper>
    getVehicleById(@PathVariable Long vehicleId)
    {
        return vehicleService.getVehicleById(vehicleId);
    }

    @DeleteMapping("/admin/vehicles/{vehicleId}")
    public ResponseEntity<ResponseWrapper>
    deleteVehicleById(@PathVariable Long vehicleId)
    {
        return vehicleService.deleteVehicleById(vehicleId);
    }

    @PutMapping("/admin/vehicles/{vehicleId}")
    public ResponseEntity<ResponseWrapper>
    updateVehicleById(@PathVariable Long vehicleId,@RequestBody Vehicle vehicle)
    {
        return vehicleService.updateVehicleById(vehicleId, vehicle);
    }

    @PutMapping("/admin/vehicles/{vehicleId}/assign-route/{routeId}")
    public ResponseEntity<ResponseWrapper>
    assignVehicleToRoute(@PathVariable Long vehicleId, @PathVariable Long routeId)
    {
        return vehicleService.assignVehicleToRoute(vehicleId, routeId);
    }
    
    @PutMapping("/admin/vehicles/{vehicleId}/status/{status}")
    public ResponseEntity<ResponseWrapper>
    assignVehicleStatus(@PathVariable Long vehicleId,@PathVariable String status)
    {
        return vehicleService.assignVehicleStatus(vehicleId, status);
    }
    
}
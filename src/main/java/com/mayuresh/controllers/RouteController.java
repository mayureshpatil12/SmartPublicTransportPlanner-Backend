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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mayuresh.entities.Route;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.services.RouteService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class RouteController {
	
	@Autowired
	RouteService routeService;
	
	
	@GetMapping("/admin/routes")
	public ResponseEntity<ResponseWrapper> getAllRoutes()
	{
		return routeService.getAllRoutes();
	}

	@GetMapping("/routes")
	public ResponseEntity<ResponseWrapper> getAllRoutesForUser()
	{
		return routeService.getAllRoutes();
	}

	@GetMapping("/routes/search")
	public ResponseEntity<ResponseWrapper> searchRoutes(@RequestParam String source, @RequestParam String destination)
	{
		return routeService.searchRoutes(source, destination);
	}

	@GetMapping("/routes/filter")
	public ResponseEntity<ResponseWrapper> filterRoutes(
			@RequestParam(required = false) String source,
			@RequestParam(required = false) String destination,
			@RequestParam(required = false) String vehicleType,
			@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String sortDirection)
	{
		return routeService.filterRoutes(source, destination, vehicleType, sortBy, sortDirection);
	}

	@GetMapping("/routes/suggestions")
	public ResponseEntity<ResponseWrapper> getRouteSuggestions(@RequestParam String field, @RequestParam(required = false) String query)
	{
		return routeService.getRouteSuggestions(field, query);
	}
	
	@PostMapping("/admin/routes")
	public ResponseEntity<ResponseWrapper> addRoute(@RequestBody Route route)
	{
		return routeService.addRoute(route);
	}
	
	@GetMapping("/admin/routes/{routeId}")
	public ResponseEntity<ResponseWrapper> getRouteById(@PathVariable Long routeId)
	{
		return routeService.getRouteById(routeId);
	}
	
	@DeleteMapping("/admin/routes/{routeId}")
	public ResponseEntity<ResponseWrapper> deleteRouteById(@PathVariable Long routeId)
	{
		return routeService.deleteRouteById(routeId);
	}
	
	@PutMapping("/admin/routes/{routeId}")
	public ResponseEntity<ResponseWrapper> updateRouteById(@PathVariable Long routeId, @RequestBody Route route)
	{
		return routeService.updateRouteById(routeId, route);
	}

}

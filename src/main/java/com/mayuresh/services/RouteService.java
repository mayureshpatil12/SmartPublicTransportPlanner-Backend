package com.mayuresh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mayuresh.entities.Route;
import com.mayuresh.repositiories.RouteRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;
import com.mayuresh.specification.RouteSpecification;

@Service
public class RouteService {
	
	@Autowired
	RouteRepository routeRepository;
	
	@Autowired
	UniversalResponse response;
	
	//admin
	// CRUD
	// add new route
	public ResponseEntity<ResponseWrapper> addRoute(Route route)
	{
		Route savedRoute=routeRepository.save(route);
		return response.send("route created", savedRoute, HttpStatus.CREATED);
	}
	
	// find/give all routes
	public ResponseEntity<ResponseWrapper> getAllRoutes()
	{
		List<Route> allRoutes=routeRepository.findAll();
		if(allRoutes.size()>0)
		{
			return response.send("following routes found", allRoutes, HttpStatus.OK);
		}
		else
		{
			return response.send("no routes found", allRoutes, HttpStatus.OK);
		}
	}
	
	// get route by id
	public ResponseEntity<ResponseWrapper> getRouteById(Long routeId)
	{
		Optional<Route> existingRoute=routeRepository.findById(routeId);
		if(existingRoute.isPresent())
		{
			return response.send("route by id " + routeId + "is found", existingRoute.get(), HttpStatus.OK);
		}
		else
		{
			return response.send("no route found by id " + routeId , null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	// delete route by id 
	public ResponseEntity<ResponseWrapper> deleteRouteById(Long routeId)
	{
		Optional<Route> existingRoute=routeRepository.findById(routeId);
		if(existingRoute.isPresent())
		{
			try {
				routeRepository.deleteById(routeId);
				return response.send("route by id " + routeId + " is deleted", existingRoute.get(), HttpStatus.OK);
			}
			catch(DataIntegrityViolationException exception)
			{
				return response.send("Route cannot be deleted because it is connected with vehicle, booking, feedback, report, or traffic data", existingRoute.get(), HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			return response.send("no route found by id " + routeId , null, HttpStatus.NOT_FOUND);
		}
	}
	
	//update route by id
	public ResponseEntity<ResponseWrapper> updateRouteById(Long routeId, Route route) 
	{
		Optional<Route> existingRoute=routeRepository.findById(routeId);
		if(existingRoute.isPresent())
		{
			route.setRouteId(routeId);
			Route savedRoute=routeRepository.save(route);
			return response.send("route by id " + routeId + "is updated", savedRoute, HttpStatus.OK);
		}
		else
		{
			return response.send("no route found by id" + routeId , null, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	// user 
	// features (business logic)
//	this is first priority method then will do others later
	public ResponseEntity<ResponseWrapper> searchRoutes(String source, String destination)
	{
	 List<Route> routesBySourceAndDestination = routeRepository.findBySourceAndDestination(source, destination);

	    if(routesBySourceAndDestination.size() > 0)
	    {
	        return response.send("Routes found from " + source + " to " + destination, routesBySourceAndDestination, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found from " + source + " to " + destination, null, HttpStatus.NOT_FOUND);
	    }
	}

	public ResponseEntity<ResponseWrapper> filterRoutes(String source, String destination, String vehicleType, String sortBy, String sortDirection)
	{
		Specification<Route> allRouteFilters=Specification.where(
				RouteSpecification.hasSource(source)
				.and(RouteSpecification.hasDestination(destination))
				.and(RouteSpecification.hasVehicleType(vehicleType))
				.and(RouteSpecification.sortByRoute(sortBy, sortDirection))
				);
		List<Route> filteredRoutes = routeRepository.findAll(allRouteFilters);

		if(filteredRoutes.size() > 0)
		{
			return response.send("Routes found", filteredRoutes, HttpStatus.OK);
		}
		else
		{
			return response.send("No routes found", filteredRoutes, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseWrapper> getRouteSuggestions(String field, String query)
	{
		List<String> suggestions;
		String searchText = query == null ? "" : query.trim();

		if(field.equalsIgnoreCase("destination"))
		{
			suggestions = routeRepository.findDestinationSuggestions(searchText);
		}
		else
		{
			suggestions = routeRepository.findSourceSuggestions(searchText);
		}

		if(suggestions.size() > 0)
		{
			return response.send("Suggestions found", suggestions, HttpStatus.OK);
		}
		else
		{
			return response.send("No such " + field + " exists", suggestions, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseWrapper> getRoutesBySource(String source)
	{
		List<Route> routesBySource=routeRepository.findBySource(source);
		if(routesBySource.size() > 0)
	    {
	        return response.send("Routes found from " + source, routesBySource, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found from " + source, null, HttpStatus.NOT_FOUND);
	    }
		
	}
	
	public ResponseEntity<ResponseWrapper> getRoutesByDestination(String destination)
	{
		List<Route> routesByDestination=routeRepository.findByDestination(destination);
		if(routesByDestination.size() > 0)
	    {
	        return response.send("Routes found from " + destination , routesByDestination, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found from " + destination, null, HttpStatus.NOT_FOUND);
	    }
	
	}
	
	public ResponseEntity<ResponseWrapper> findRoutesByIntermediateStop(String stop)
	{
	    List<Route> routesByIntermediateStop = routeRepository.findByIntermediateStopsContaining(stop);

	    if(routesByIntermediateStop.size() > 0)
	    {
	        return response.send("Routes containing stop " + stop + " found", routesByIntermediateStop, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found containing stop " + stop, null, HttpStatus.NOT_FOUND);
	    }
	}
	
	public ResponseEntity<ResponseWrapper> getRoutesByMaxDistance(double distance)
	{
		List<Route> routesByMaxDistance =routeRepository.findByDistanceLessThan(distance);
		
		if(routesByMaxDistance.size() > 0)
	    {
	        return response.send( "Routes within distance " + distance + " found", routesByMaxDistance, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found within distance " + distance, null, HttpStatus.NOT_FOUND);
	    }
	}
	
	
	// advanced one (which includes sql query in routes repo and also @query and @param annotations)
	public ResponseEntity<ResponseWrapper> findRoutesByVehicleType(String vehicleType)
	{
		List<Route> routesByVehicleType=routeRepository.findRoutesByVehicleType(vehicleType);
		if(routesByVehicleType.size() > 0)
	    {
	        return response.send( "Routes with vehicle type " + vehicleType + " found", routesByVehicleType, HttpStatus.OK);
	    }
	    else
	    {
	        return response.send("No routes found with vehicle type " + vehicleType, null, HttpStatus.NOT_FOUND);
	    }
	}

	

	
}

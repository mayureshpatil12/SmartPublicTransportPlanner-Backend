package com.mayuresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/admin/routes")
    public ResponseEntity<ResponseWrapper>
    addRoute(@RequestBody Route route)
    {
        return routeService.addRoute(route);
    }


    @GetMapping("/admin/routes/{routeId}")
    public ResponseEntity<ResponseWrapper>
    getRouteById(@PathVariable Long routeId)
    {
        return routeService.getRouteById(routeId);
    }


    @DeleteMapping("/admin/routes/{routeId}")
    public ResponseEntity<ResponseWrapper>
    deleteRouteById(@PathVariable Long routeId)
    {
        return routeService.deleteRouteById(routeId);
    }


    @PutMapping("/admin/routes/{routeId}")
    public ResponseEntity<ResponseWrapper>
    updateRouteById( @PathVariable Long routeId, @RequestBody Route route)
    {
        return routeService.updateRouteById(routeId, route);
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


}

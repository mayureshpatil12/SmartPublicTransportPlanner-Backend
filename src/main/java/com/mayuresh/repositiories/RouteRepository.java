package com.mayuresh.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, JpaSpecificationExecutor<Route>{

	List<Route> findBySourceAndDestination(String source, String destination);
	
	List<Route> findBySource(String source);
	
	List<Route> findByDestination(String destination);
	
	List<Route> findByIntermediateStopsContaining(String stop);
	
	List<Route> findByDistanceLessThan(double distance);
	
	@Query("SELECT DISTINCT r FROM Route r JOIN r.vehicles v WHERE v.vehicleType = :vehicleType")
	List<Route> findRoutesByVehicleType(@Param("vehicleType") String vehicleType);

	@Query("SELECT DISTINCT r.source FROM Route r WHERE LOWER(r.source) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY r.source")
	List<String> findSourceSuggestions(@Param("query") String query);

	@Query("SELECT DISTINCT r.destination FROM Route r WHERE LOWER(r.destination) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY r.destination")
	List<String> findDestinationSuggestions(@Param("query") String query);
}

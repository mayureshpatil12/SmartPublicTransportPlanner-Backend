package com.mayuresh.specification;

import org.springframework.data.jpa.domain.Specification;

import com.mayuresh.entities.Route;
import com.mayuresh.entities.Vehicle;

import jakarta.persistence.criteria.Join;

public class RouteSpecification {

	public static Specification<Route> hasSource(String source)
	{
		return (root, query, cb)->
		{
			if(source==null || source.isBlank())
			{
				return null;
			}
			else
			{
				return cb.like(cb.lower(root.get("source")), "%"+source.toLowerCase()+"%");
			}
		};
	}

	public static Specification<Route> hasDestination(String destination)
	{
		return (root, query, cb)->
		{
			if(destination==null || destination.isBlank())
			{
				return null;
			}
			else
			{
				return cb.like(cb.lower(root.get("destination")), "%"+destination.toLowerCase()+"%");
			}
		};
	}

	public static Specification<Route> hasVehicleType(String vehicleType)
	{
		return (root, query, cb)->
		{
			if(vehicleType==null || vehicleType.isBlank() || vehicleType.equalsIgnoreCase("All"))
			{
				return null;
			}
			else
			{
				query.distinct(true);
				Join<Route, Vehicle> routeVehicleJoin=root.join("vehicles");
				return cb.equal(cb.lower(routeVehicleJoin.get("vehicleType")), vehicleType.toLowerCase());
			}
		};
	}

	public static Specification<Route> sortByRoute(String sortBy, String sortDirection)
	{
		return (root, query, cb)->
		{
			if(sortBy==null || sortBy.isBlank())
			{
				return null;
			}

			if(sortBy.equals("distance") || sortBy.equals("sourceTime") || sortBy.equals("destinationTime"))
			{
				if(sortDirection!=null && sortDirection.equalsIgnoreCase("desc"))
				{
					query.orderBy(cb.desc(root.get(sortBy)));
				}
				else
				{
					query.orderBy(cb.asc(root.get(sortBy)));
				}
			}

			return null;
		};
	}
}

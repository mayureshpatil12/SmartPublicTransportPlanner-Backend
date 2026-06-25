package com.mayuresh.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUserUserId(Long userId);

	List<Booking> findByVehicleVehicleIdAndStatusNot(Long vehicleId, String status);
}

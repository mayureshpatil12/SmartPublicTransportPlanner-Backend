package com.mayuresh.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.TrafficData;

@Repository
public interface TrafficDataRepository extends JpaRepository<TrafficData, Long>{

}

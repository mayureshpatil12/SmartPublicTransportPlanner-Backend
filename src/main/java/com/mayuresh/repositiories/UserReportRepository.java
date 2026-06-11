package com.mayuresh.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.UserReport;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long>{

}

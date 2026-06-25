package com.mayuresh.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByUserEmailOrderByCreatedAtDesc(String userEmail);
}

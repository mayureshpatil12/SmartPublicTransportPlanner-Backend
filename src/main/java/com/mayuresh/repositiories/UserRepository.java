package com.mayuresh.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayuresh.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}

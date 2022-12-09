package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findById(Long id);
	
}
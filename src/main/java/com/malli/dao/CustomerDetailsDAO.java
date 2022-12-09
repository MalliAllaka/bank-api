package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.CustomerDetails;

@Repository
public interface CustomerDetailsDAO extends JpaRepository<CustomerDetails, Long> {
	
	Optional<CustomerDetails> findById(Long id);
	
}
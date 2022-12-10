package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.AccountType;
import com.malli.model.ApplicationConstants;

@Repository
public interface ApplicationConstantsDAO extends JpaRepository<ApplicationConstants, Long> {
	
	Optional<ApplicationConstants> findById(Long id);

	ApplicationConstants findByKey(String key);
	
	
}
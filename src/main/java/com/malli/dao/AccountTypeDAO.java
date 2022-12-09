package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.AccountType;
import com.malli.model.DAOUser;


@Repository
public interface AccountTypeDAO extends JpaRepository<AccountType, Long> {
	
	Optional<AccountType> findById(Long id);
	
}
package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.Transactions;

@Repository
public interface TransactionsDAO extends JpaRepository<Transactions, Long> {
	
	Optional<Transactions> findById(Long id);
	
}
package com.malli.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.malli.model.Transactions;

@Repository
public interface TransactionsDAO extends JpaRepository<Transactions, Long> {
	
	Optional<Transactions> findById(Long id);

    @Query(value = "select tr from Transactions tr join tr.customer c where c.id = ?1 ")
	List<Transactions> findbyCustomerId(Long customerId, Pageable pageable);
	
}
package com.malli.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.malli.model.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findById(Long id);

    
    @Query(value = "select c from Customer c join c.customerDetails cd where TRIM(c.accountNumber) like %?1% or cd.firstName like %?1% or cd.phoneNo like %?1% ")
   	List<Customer> searchCustomers(String searchText, Pageable pageable);


    @Query(value = "select c from Customer c join c.customerDetails cd where c.accountNumber=?1")
	Customer findByAccountNumber(Integer accountNo);
	
}
package com.malli.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.DAOUser;
import com.malli.model.Transactions;

@Repository
public interface UserDao extends JpaRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
	DAOUser findById(Long username);
	
    @Query(value = "select u from DAOUser u join u.customer c ")
	List<DAOUser> findbyCustomers(Pageable pageable);
    
    @Query(value = "select u from DAOUser u join u.employee e ")
   	List<DAOUser> findbyEmployee(Pageable pageable);

	@Query(value = "select u from DAOUser u join u.employee e where e.firstName like %?1% or e.lastName like %?1% or e.phoneNo like %?1% ")
	List<DAOUser> searchEmployees(String searchText, Pageable pageable);
	
    @Query(value = "select u from DAOUser u join u.customer c where c.id = ?1")
	DAOUser findbyCustomerId(Long customerId);


	
}
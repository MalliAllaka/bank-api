package com.malli.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.malli.model.Customer;
import com.malli.model.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findById(Long id);
	
	@Query(value = "select e from Employee e where e.firstName like %?1% or e.lastName like %?1% or e.phoneNo like %?1% ")
	List<Employee> searchEmployees(String searchText, Pageable pageable);
}

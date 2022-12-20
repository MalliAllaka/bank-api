package com.malli.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findById(Long id);
}

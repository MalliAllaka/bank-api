package com.malli.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.malli.model.DAOUser;

@Repository
public interface UserDao extends JpaRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
	DAOUser findById(Long username);

	
}
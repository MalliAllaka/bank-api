package com.malli.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.malli.dao.UserDao;
import com.malli.model.DAOUser;

@Service
public class UsersService {
	
	@Autowired
	private UserDao userDao;

	public DAOUser getUserDetails(Long userId) throws Exception {
		DAOUser user = userDao.findById(userId);
		if (user == null) {
			throw new Exception("User not found with username: " + userId);
		}
		return user;
	}

}

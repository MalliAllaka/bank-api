package com.malli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malli.model.DAOUser;
import com.malli.service.UsersService;

@RestController
public class HelloWorldController {

	@Autowired
	private UsersService usersService;
	
	@RequestMapping({ "/hello" })
	public DAOUser firstPage(Long userId)  throws Exception{
		try {
			DAOUser user = usersService.getUserDetails(userId);
			return user;
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
	}

}
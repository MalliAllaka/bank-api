package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.AccountType;
import com.malli.model.DAOUser;
import com.malli.service.UsersService;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController extends CommonController{
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<DAOUser> findAll(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)  throws Exception{
		List<DAOUser> userList = new ArrayList<DAOUser>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
			userList = usersService.findAll(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return userList;
	}
	
	@RequestMapping(value = "/findAllCustomers", method = RequestMethod.GET)
	public List<DAOUser> findAllCustomers(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)  throws Exception{
		List<DAOUser> userList = new ArrayList<DAOUser>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
			userList = usersService.findAllCustomers(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return userList;
	}

	
	@RequestMapping(value = "/findAllEmployees", method = RequestMethod.GET)
	public List<DAOUser> findAllEmployees(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)  throws Exception{
		List<DAOUser> userList = new ArrayList<DAOUser>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
			userList = usersService.findAllEmployees(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return userList;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public DAOUser create(@RequestBody DAOUser user)  throws Exception{
		try {
			user = usersService.create(user);
			return user;
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
	}
	
	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	public DAOUser findByUsername(@RequestParam String username)  throws Exception{
		DAOUser user = null;
		try {
			user = usersService.getUserDetailsByUsername(username);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return user;
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public DAOUser addEmployee(@RequestBody DAOUser user)  throws Exception{
		try {
			user = usersService.addEmployee(user);
			return user;
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
	}
	
}
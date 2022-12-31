package com.malli.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.AccountType;
import com.malli.model.DAOUser;
import com.malli.model.Employee;
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
	
	
	@RequestMapping(value = "/searchEmployees", method = RequestMethod.GET)
	public List<DAOUser> searchEmployees(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,@RequestParam String searchText)  throws Exception{
		List<DAOUser> userList = new ArrayList<DAOUser>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

			userList = usersService.searchEmployees(searchText,pageable);
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
			if(user != null && !Objects.isNull(user.getId()) && user.getId() != 0) {
				user = usersService.updateEmployee(user);
			} else {
				user = usersService.addEmployee(user);
			}
			return user;
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
	}

	@RequestMapping(value = "/findUserById", method = RequestMethod.GET)
	public DAOUser findUserById(@RequestParam Long id)  throws Exception{
		DAOUser user = null;
		try {
			user = usersService.getUserDetails(id);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return user;
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updatePassword(@RequestBody DAOUser user)  throws Exception{
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			res  = usersService.updatePassword(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
	}
	
}
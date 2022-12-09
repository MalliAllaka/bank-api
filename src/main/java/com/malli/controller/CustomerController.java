package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.Customer;
import com.malli.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/customer/")
public class CustomerController extends CommonController{
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Customer> findAll(@RequestBody Pageable pageable)  throws Exception{
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = customerService.findAll(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customerList;

	}

}

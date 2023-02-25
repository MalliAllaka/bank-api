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
import com.malli.model.Customer;
import com.malli.model.DAOUser;
import com.malli.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/customer/")
public class CustomerController extends CommonController{
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Customer> findAll(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)  throws Exception{
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

			customerList = customerService.findAll(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customerList;

	}
	
	@RequestMapping(value = "/searchCustomers", method = RequestMethod.GET)
	public List<Customer> searchCustomers(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,@RequestParam String searchText)  throws Exception{
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

			customerList = customerService.searchCustomers(searchText,pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customerList;

	}
	

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.GET)
	public Customer searchCustomer(@RequestParam Long id)  throws Exception{
		Customer customer = new Customer();
		try {
			customer = customerService.searchCustomer(id);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customer;

	}
	
	@RequestMapping(value = "/findByAccountNo", method = RequestMethod.GET)
	public Customer findByUsername(@RequestParam Integer accountNo)  throws Exception{
		Customer customer = new Customer();
		try {
			customer = customerService.findByAccountNumber(accountNo);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customer;
	}
	
	
	

}

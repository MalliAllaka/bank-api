package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.Customer;
import com.malli.model.DAOUser;
import com.malli.service.CustomerDetailsService;
import com.malli.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/customer/")
public class CustomerDetailsController extends CommonController{
	
	@Autowired
	private CustomerDetailsService customerDetailsService;
	

}

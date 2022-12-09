package com.malli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malli.dao.CustomerDetailsDAO;

@Service
public class CustomerDetailsService {
	
	@Autowired
	private CustomerDetailsDAO customerDetailsDAO;

}

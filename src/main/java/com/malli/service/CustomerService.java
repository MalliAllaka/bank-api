package com.malli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.malli.dao.CustomerDAO;
import com.malli.model.Customer;
import com.malli.model.Transactions;

@Service
public class CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	public List<Customer> findAll(Pageable pageable) throws Exception {
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Page<Customer> customerListPage = customerDAO.findAll(pageable);
			customerList = customerListPage.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return customerList;
	}

	public List<Customer> searchCustomers(String searchText, Pageable pageable) {
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = customerDAO.searchCustomers(searchText,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return customerList;
	}

	public Customer searchCustomer(Long id) throws Exception {
		Optional<Customer> customerOpt = customerDAO.findById(id);
		if (!customerOpt.isPresent()) {
			throw new Exception("Customer not found for id : " + id);
		}
		return customerOpt.get();
	} 
}

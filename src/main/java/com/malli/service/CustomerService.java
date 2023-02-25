package com.malli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.malli.dao.CustomerDAO;
import com.malli.dao.UserDao;
import com.malli.model.Customer;
import com.malli.model.DAOUser;
import com.malli.model.Transactions;

@Service
public class CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private UserDao userDao;

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
		try {
			Optional<Customer> customerOpt = customerDAO.findById(id);
			Customer customer =  customerOpt.get();
			if (!customerOpt.isPresent()) {
				throw new Exception("Customer not found for id : " + id);
			}
			DAOUser user = userDao.findbyCustomerId(customer.getId());
			customer.setUser(user);
			
			return customer;
		} catch (Exception e) {
			throw new Exception("Customer not found for id : " + id);
		}
	}

	public Customer findByAccountNumber(Integer accountNo) throws Exception {
		try {

			Customer customer = customerDAO.findByAccountNumber(accountNo);
			
			return customer;
		} catch (Exception e) {
			throw new Exception("Customer not found for id : " + accountNo);
		}
	} 
}

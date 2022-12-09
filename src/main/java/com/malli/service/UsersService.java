package com.malli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malli.dao.CustomerDAO;
import com.malli.dao.CustomerDetailsDAO;
import com.malli.dao.UserDao;
import com.malli.model.Customer;
import com.malli.model.CustomerDetails;
import com.malli.model.DAOUser;

@Service
public class UsersService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CustomerDetailsDAO customerDetailsDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	public DAOUser getUserDetails(Long userId) throws Exception {
		DAOUser user = userDao.findById(userId);
		if (user == null) {
			throw new Exception("User not found with username: " + userId);
		}
		return user;
	}

	public List<DAOUser> findAll(Pageable pageable)throws Exception {
		List<DAOUser> userList =new ArrayList<DAOUser>();
		try {
			Page<DAOUser> userListPage = userDao.findAll(pageable);
			userList = userListPage.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return userList;
	}

	public DAOUser create(DAOUser user) throws Exception {
		try {
			CustomerDetails customerDetails = customerDetailsDAO.save(user.getCustomer().getCustomerDetails());
			Customer customer = user.getCustomer();
			customer.setCustomerDetails(customerDetails);
			customer = customerDAO.save(user.getCustomer());
			user.setCustomer(customer);
			user.setPassword(bcryptEncoder.encode(user.getNewPassword()));
			userDao.save(user);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

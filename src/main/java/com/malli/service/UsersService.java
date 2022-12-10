package com.malli.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malli.dao.AccountTypeDAO;
import com.malli.dao.ApplicationConstantsDAO;
import com.malli.dao.CustomerDAO;
import com.malli.dao.CustomerDetailsDAO;
import com.malli.dao.UserDao;
import com.malli.model.AccountType;
import com.malli.model.ApplicationConstants;
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
	
	@Autowired
	private AccountTypeDAO accountTypeDAO;

	@Autowired
	private ApplicationConstantsDAO applicationConstantsDAO;
	
	public DAOUser getUserDetails(Long userId) throws Exception {
		DAOUser user = userDao.findById(userId);
		if (user == null) {
			throw new Exception("User not found with username: " + userId);
		}
		return user;
	}
	
	public DAOUser getUserDetailsByUsername(String username) throws Exception {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new Exception("User not found with username: " + username);
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
			Customer customer = user.getCustomer();
			AccountType accountType = new AccountType();
			Optional<AccountType> accountTypeOpt = accountTypeDAO.findById(customer.getAccountType().getId());
			accountType = accountTypeOpt.get();
			ApplicationConstants currentAccountNumber=applicationConstantsDAO.findByKey("current_account_number");
			ApplicationConstants currentCustomer=applicationConstantsDAO.findByKey("current_customer_id");
			Integer can = Integer.parseInt(currentAccountNumber.getValue())+1;
			currentAccountNumber.setValue(can.toString());
			Integer cc = Integer.parseInt(currentCustomer.getValue())+1;
			currentCustomer.setValue(cc.toString());
			customer.setAccountNumber(can);
			customer.setCustomerId(cc.toString());
			customer.setAccountType(accountType);
			customer.setBalance(accountType.getMinBalance());
			customer.setOpenDate(new Date());
			CustomerDetails customerDetails = customerDetailsDAO.save(user.getCustomer().getCustomerDetails());
			
			customer.setCustomerDetails(customerDetails);
			customer = customerDAO.save(user.getCustomer());
			user.setUsername(customerDetails.getFirstName().toLowerCase()+cc);
			user.setCustomer(customer);
			user.setPassword(bcryptEncoder.encode(user.getNewPassword()));
			userDao.save(user);
			
//			applicationConstantsDAO.save(currentAccountNumber);
//			applicationConstantsDAO.save(currentCustomer);

			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

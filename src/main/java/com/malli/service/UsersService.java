package com.malli.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malli.dao.AccountTypeDAO;
import com.malli.dao.ApplicationConstantsDAO;
import com.malli.dao.CustomerDAO;
import com.malli.dao.CustomerDetailsDAO;
import com.malli.dao.EmployeeDAO;
import com.malli.dao.UserDao;
import com.malli.model.AccountType;
import com.malli.model.ApplicationConstants;
import com.malli.model.Customer;
import com.malli.model.CustomerDetails;
import com.malli.model.DAOUser;
import com.malli.model.Employee;

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
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String append = dateFormat.format(date);
			Integer cId = Integer.parseInt(append+can.toString());

			
			customer.setAccountNumber(cId);
			customer.setCustomerId(cc.toString());
			customer.setAccountType(accountType);
			customer.setBalance(accountType.getMinBalance());
			customer.setOpenDate(new Date());
			CustomerDetails customerDetails = customerDetailsDAO.save(user.getCustomer().getCustomerDetails());
			
			customer.setCustomerDetails(customerDetails);
			customer = customerDAO.save(user.getCustomer());
			String username = customerDetails.getFirstName().toLowerCase().replaceAll("\\s", "_")+"."+cc;
//			user.setUsername(customerDetails.getFirstName().toLowerCase()+cc);
			user.setUsername(username);
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

	public List<DAOUser> findAllCustomers(Pageable pageable) throws Exception {
		List<DAOUser> userList =new ArrayList<DAOUser>();
		try {
			userList = userDao.findbyCustomers(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return userList;
	}
	
	public List<DAOUser> findAllEmployees(Pageable pageable) throws Exception {
		List<DAOUser> userList =new ArrayList<DAOUser>();
		try {
			userList = userDao.findbyEmployee(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return userList;
	}

	public List<DAOUser> searchEmployees(String searchText, Pageable pageable)  throws Exception  {
		List<DAOUser> userList =new ArrayList<DAOUser>();
		try {
			userList = userDao.searchEmployees(searchText,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return userList;
	}

	public DAOUser addEmployee(DAOUser user)  throws Exception  {
		try {
			ApplicationConstants currentEmployee =applicationConstantsDAO.findByKey("current_employee_id");
			Integer cc = Integer.parseInt(currentEmployee.getValue())+1;
			currentEmployee.setValue(cc.toString());
			Employee employee = employeeDAO.save(user.getEmployee());
			user.setUsername("employee."+cc);
			user.setEmployee(employee);
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

	public DAOUser updateEmployee(DAOUser user)  throws Exception {
		try {
			DAOUser currUser = userDao.findById(user.getId());
			if (user != null) {
				Employee currEmployee = currUser.getEmployee();
				Employee employee = user.getEmployee();
				if(StringUtils.isNotBlank(user.getUserType())) {
					currUser.setUserType(user.getUserType());
				}
				if(StringUtils.isNotBlank(user.getNewPassword())) {
					currUser.setPassword(bcryptEncoder.encode(user.getNewPassword()));
				}
				currEmployee.setFirstName(employee.getFirstName());
				currEmployee.setLastName(employee.getLastName());
				currEmployee.setAddress(employee.getAddress());
				currEmployee.setAge(employee.getAge());
				currEmployee.setCountry(employee.getCountry());
				currEmployee.setEmail(employee.getEmail());
				currEmployee.setPhoneNo(employee.getPhoneNo());
				
				employeeDAO.save(currEmployee);

				userDao.save(currUser);

				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public Map<String, Object> updatePassword(DAOUser user)  throws Exception {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			DAOUser currUser = userDao.findById(user.getId());
			if (currUser != null) {
				try {
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(currUser.getUsername(), user.getCurrentPassword()));
				} catch (Exception e) {
					status.put("status", false);
					status.put("message", "current password is incorrect");
					return status;
				}
				if(StringUtils.isNotBlank(user.getNewPassword())) {
					currUser.setPassword(bcryptEncoder.encode(user.getNewPassword()));
					userDao.save(currUser);
					status.put("status", true);
					status.put("message", "updated successfully");
					return status;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		status.put("status", false);
		status.put("message", "Fail to update password");
		return status;
	}

}

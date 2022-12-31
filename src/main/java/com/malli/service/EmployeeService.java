package com.malli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malli.dao.ApplicationConstantsDAO;
import com.malli.dao.EmployeeDAO;
import com.malli.dao.UserDao;
import com.malli.model.ApplicationConstants;
import com.malli.model.DAOUser;
import com.malli.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private ApplicationConstantsDAO applicationConstantsDAO;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	public List<Employee> findAll(Pageable pageable) throws Exception {
		List<Employee> customerList = new ArrayList<Employee>();
		try {
			Page<Employee> customerListPage = employeeDAO.findAll(pageable);
			customerList = customerListPage.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return customerList;
	}

	public List<Employee> searchEmployees(String searchText, Pageable pageable) {
		List<Employee> customerList = new ArrayList<Employee>();
		try {
			customerList = employeeDAO.searchEmployees(searchText,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return customerList;
	}

	public Employee searchEmployee(Long id) throws Exception {
		Optional<Employee> customerOpt = employeeDAO.findById(id);
		if (!customerOpt.isPresent()) {
			throw new Exception("Employee not found for id : " + id);
		}
		return customerOpt.get();
	}

	public DAOUser addEmployee(DAOUser user) {
		try {
			ApplicationConstants currentEmployee =applicationConstantsDAO.findByKey("current_employee_id");
			Integer cc = Integer.parseInt(currentEmployee.getValue())+1;
			currentEmployee.setValue(cc.toString());
			Employee employee = employeeDAO.save(user.getEmployee());
			user.setUsername(employee.getFirstName().toLowerCase()+cc);
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
}

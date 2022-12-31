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
import com.malli.model.DAOUser;
import com.malli.model.Employee;
import com.malli.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/employee/")
public class EmployeeController extends CommonController{
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Employee> findAll(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)  throws Exception{
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

			employeeList = employeeService.findAll(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return employeeList;

	}
	
	@RequestMapping(value = "/searchEmployees", method = RequestMethod.GET)
	public List<Employee> searchEmployees(@RequestParam Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,@RequestParam String searchText)  throws Exception{
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

			employeeList = employeeService.searchEmployees(searchText,pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return employeeList;

	}

	@RequestMapping(value = "/searchEmployee", method = RequestMethod.GET)
	public Employee searchEmployee(@RequestParam Long id)  throws Exception{
		Employee customer = new Employee();
		try {
			customer = employeeService.searchEmployee(id);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return customer;

	}
	
	

}

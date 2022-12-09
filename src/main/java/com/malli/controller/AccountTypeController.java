package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.AccountType;
import com.malli.service.AccountTypeService;

@RestController
@CrossOrigin
@RequestMapping("/accounttype/")
public class AccountTypeController extends CommonController {

	@Autowired
	private AccountTypeService accountTypeService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public AccountType create(@RequestBody AccountType accountType)  throws Exception{
		try {
			accountType = accountTypeService.create(accountType);
			return accountType;
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
	}
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<AccountType> findAll()  throws Exception{
		List<AccountType> accounts = new ArrayList<AccountType>();
		try {
			accounts = accountTypeService.findAll();
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return accounts;

	}

}
package com.malli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malli.dao.AccountTypeDAO;
import com.malli.model.AccountType;

@Service
public class AccountTypeService {
	
	@Autowired
	private AccountTypeDAO accountTypeDAO;

	public AccountType create(AccountType accountType) throws Exception {
		try {
			accountTypeDAO.save(accountType);
			return accountType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<AccountType> findAll() {
		try {
			List<AccountType>  list = accountTypeDAO.findAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<AccountType>();
	}

}

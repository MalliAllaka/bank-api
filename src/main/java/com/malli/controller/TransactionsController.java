package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.Transactions;
import com.malli.service.CustomerService;
import com.malli.service.TransactionsService;

@RestController
@CrossOrigin
@RequestMapping("/transactions/")
public class TransactionsController extends CommonController{
	
	@Autowired
	private TransactionsService transactionsService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Transactions> findAll(@RequestBody Pageable pageable)  throws Exception{
		List<Transactions> transactionList = new ArrayList<Transactions>();
		try {
			transactionList = transactionsService.findAll(pageable);
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return transactionList;

	}

}

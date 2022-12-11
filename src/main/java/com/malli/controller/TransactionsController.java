package com.malli.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(value = "/withdrawAmount", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> withdrawAmount(@RequestBody Transactions transactions)  throws Exception{
		Map<String, Object> response = new HashMap<>();
		try {
			transactions = transactionsService.withdrawAmount(transactions);
			response.put("transactions", transactions);
		} catch (Exception e) {
			response.put("errorMessage", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	
	

}

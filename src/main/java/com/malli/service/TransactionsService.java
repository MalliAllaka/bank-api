package com.malli.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malli.dao.TransactionsDAO;
import com.malli.model.Transactions;

@Service
public class TransactionsService {

	@Autowired
	private TransactionsDAO transactionsDAO;
	
	public Transactions getTransactions(Long id) throws Exception {
		Optional<Transactions> transactions = transactionsDAO.findById(id);
		if (!transactions.isPresent()) {
			throw new Exception("Transaction not found for id : " + id);
		}
		return transactions.get();
	}
}

package com.malli.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.malli.common.SecurityContextUtils;
import com.malli.dao.CustomerDAO;
import com.malli.dao.TransactionsDAO;
import com.malli.dao.UserDao;
import com.malli.model.Customer;
import com.malli.model.DAOUser;
import com.malli.model.Transactions;

@Service
public class TransactionsService {

	@Autowired
	private TransactionsDAO transactionsDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private UserDao userDao;
	
	public Transactions getTransactions(Long id) throws Exception {
		Optional<Transactions> transactions = transactionsDAO.findById(id);
		if (!transactions.isPresent()) {
			throw new Exception("Transaction not found for id : " + id);
		}
		return transactions.get();
	}

	public List<Transactions> findAll(Pageable pageable)throws Exception {
		List<Transactions> transactionList =new ArrayList<Transactions>();
		try {
			Page<Transactions> transactionListPage = transactionsDAO.findAll(pageable);
			transactionList = transactionListPage.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return transactionList;
	}

	public Transactions withdrawAmount(Transactions transactions) throws Exception {
		try {
			Optional<Customer> customerOpt = customerDAO.findById(transactions.getCustomer().getId());
			Customer customer = customerOpt.get();
			if(customer.getBalance() >= transactions.getAmount()) {
				String userName = SecurityContextUtils.getUserId();
				transactions.setDate(new Date());
				transactions.setFrom(userName);
				transactions = transactionsDAO.save(transactions);
				customer.setBalance(customer.getBalance() - transactions.getAmount());
				customer = customerDAO.save(customer);
				DAOUser user = userDao.findbyCustomerId(customer.getId());
				customer.setUser(user);
				transactions.setCustomer(customer);
				return transactions;
			} else {
				throw new Exception("low Balance");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Transactions depositeAmount(Transactions transactions) throws Exception {
		try {
			Optional<Customer> customerOpt = customerDAO.findById(transactions.getCustomer().getId());
			Customer customer = customerOpt.get();
			transactions.setDate(new Date());
			transactions = transactionsDAO.save(transactions);
			customer.setBalance(customer.getBalance() + transactions.getAmount());
			customer = customerDAO.save(customer);
			DAOUser user = userDao.findbyCustomerId(customer.getId());
			customer.setUser(user);
			transactions.setCustomer(customer);
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<Transactions> findbyCustomerId(Long customerId, Pageable pageable) throws Exception {
		List<Transactions> transactionList =new ArrayList<Transactions>();
		try {
			transactionList = transactionsDAO.findbyCustomerId(customerId,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return transactionList;
	}

	public Transactions transferAmount(Transactions transactions) throws Exception {
		try {
			Transactions transaction= new Transactions();
			Optional<Customer> customerOpt = customerDAO.findById(transactions.getCustomer().getId());
			Customer customer = customerOpt.get();
			Optional<Customer> depositcustomerOpt = customerDAO.findById(transactions.getDepositCustomerId());
			Customer depositcustomer = depositcustomerOpt.get();
			
			if(depositcustomer.getBalance() > transactions.getAmount()) {
				String fullName = depositcustomer.getCustomerDetails().getFirstName() + depositcustomer.getCustomerDetails().getFirstName() != null ?  depositcustomer.getCustomerDetails().getFirstName()  : "";
	
				transaction.setDate(new Date());
				transaction.setCustomer(transactions.getCustomer());
				transaction.setAmount(transactions.getAmount());
				transaction.setType("Deposite");
				transaction.setMethod("Transfer");
				transaction.setFrom(fullName);
				transaction.setRemark(transactions.getRemark());
	
				transaction = transactionsDAO.save(transaction);
				
				depositcustomer.setBalance(depositcustomer.getBalance() - transaction.getAmount());
				depositcustomer = customerDAO.save(customer);
				
				customer.setBalance(customer.getBalance() + transaction.getAmount());
				customer = customerDAO.save(customer);
				
				DAOUser user = userDao.findbyCustomerId(customer.getId());
				customer.setUser(user);
				transaction.setCustomer(customer);
				return transaction;
			}
			throw new Exception("Insufficient Funds");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

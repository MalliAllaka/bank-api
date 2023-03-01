package com.malli.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.malli.common.SecurityContextUtils;
import com.malli.dao.ApplicationConstantsDAO;
import com.malli.dao.CustomerDAO;
import com.malli.dao.TransactionsDAO;
import com.malli.dao.UserDao;
import com.malli.model.ApplicationConstants;
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
	
	@Autowired
	private ApplicationConstantsDAO applicationConstantsDAO;
	
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
				ApplicationConstants currentTransaction=applicationConstantsDAO.findByKey("current_transaction_id");
				Integer can = Integer.parseInt(currentTransaction.getValue());
				can = can + 1;
				currentTransaction.setValue(can.toString());
				DateFormat dateFormat = new SimpleDateFormat("yyMM");
				Date date = new Date();
				String append = dateFormat.format(date);
				
				String userName = SecurityContextUtils.getUserId();
				transactions.setDate(new Date());
				transactions.setFrom(userName);
				Double balance = customer.getBalance() - transactions.getAmount();
				transactions.setBalance(balance);
				transactions.setId(append+can);
				transactions = transactionsDAO.save(transactions);
				customer.setBalance(balance);
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
			ApplicationConstants currentTransaction=applicationConstantsDAO.findByKey("current_transaction_id");
			Integer can = Integer.parseInt(currentTransaction.getValue());
			can = can + 1;
			currentTransaction.setValue(can.toString());
			DateFormat dateFormat = new SimpleDateFormat("yyMM");
			Date date = new Date();
			String append = dateFormat.format(date);

			Optional<Customer> customerOpt = customerDAO.findById(transactions.getCustomer().getId());
			Customer customer = customerOpt.get();
			transactions.setDate(new Date());
			Double balance = customer.getBalance() + transactions.getAmount();
			
			transactions.setId(append+can);
			transactions.setBalance(balance);
			transactions = transactionsDAO.save(transactions);
			customer.setBalance(balance);
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

	public List<Transactions> findbyCustomerId(Long customerId, String startDate, String endDate, Pageable pageable)throws Exception {
		List<Transactions> transactionList =new ArrayList<Transactions>();
		try {
			if(!StringUtils.isNotBlank(startDate) || !StringUtils.isNotBlank(endDate)) {
				transactionList = transactionsDAO.findbyCustomerId(customerId,pageable);
			} else {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date startDateTime = dateFormat.parse(startDate);
				Date endDateDateTime = dateFormat.parse(endDate);
				
				int result = startDateTime.compareTo(endDateDateTime);

				if(result == 0) {
//				    System.out.println("Both dates are equal");
				} else if (result < 0) {
//					System.out.println("Date1 is before Date2");
				} else {
					System.out.println("Date1 is after Date2");
					Date newDate = dateFormat.parse(endDate);
					endDateDateTime = startDateTime;
					startDateTime = newDate;
				}
				
				if(startDateTime!=null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(startDateTime);
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					startDateTime = cal.getTime();
				}
				if(endDateDateTime!=null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(endDateDateTime);
					cal.set(Calendar.HOUR, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					cal.set(Calendar.MILLISECOND, 0);
					endDateDateTime = cal.getTime();
				}
				transactionList = transactionsDAO.findbyCustomerId(customerId,startDateTime,endDateDateTime,pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("failed at findAll");
		}
		return transactionList;
	}

	public Transactions transferAmount(Transactions transactions) throws Exception {
		try {
			Transactions transaction= new Transactions();
			Transactions depositCustomerTransaction= new Transactions();
			ApplicationConstants currentTransaction=applicationConstantsDAO.findByKey("current_transaction_id");
			Integer can = Integer.parseInt(currentTransaction.getValue());
			can = can + 2;
			currentTransaction.setValue(can.toString());
			DateFormat dateFormat = new SimpleDateFormat("yyMM");
			Date date = new Date();
			String append = dateFormat.format(date);
			
			Optional<Customer> customerOpt = customerDAO.findById(transactions.getCustomer().getId());
			Customer customer = customerOpt.get();
			Optional<Customer> depositcustomerOpt = customerDAO.findById(transactions.getDepositCustomerId());
			Customer depositcustomer = depositcustomerOpt.get();
			if(depositcustomer.getBalance() > transactions.getAmount()) {
				String fullName = depositcustomer.getCustomerDetails().getFirstName() + depositcustomer.getCustomerDetails().getFirstName() != null ?  depositcustomer.getCustomerDetails().getFirstName()  : "";
				Integer canT = can-1;
				transaction.setId(append+canT);
				transaction.setDate(new Date());
				transaction.setCustomer(transactions.getCustomer());
				transaction.setAmount(transactions.getAmount());
				transaction.setType("Deposite");
				transaction.setMethod("Transfer");
				transaction.setFrom(fullName);
				transaction.setRemark(transactions.getRemark());
				Double balance = customer.getBalance() + transactions.getAmount();
				transaction.setBalance(balance);
				transaction = transactionsDAO.save(transaction);
				customer.setBalance(balance);
				customer = customerDAO.save(customer);
				
				depositCustomerTransaction.setId(append+can);
				depositCustomerTransaction.setDate(new Date());
				depositCustomerTransaction.setCustomer(depositcustomer);
				depositCustomerTransaction.setAmount(transactions.getAmount());
				depositCustomerTransaction.setType("Withdraw");
				depositCustomerTransaction.setMethod("Transfer");
				depositCustomerTransaction.setFrom("self");
				depositCustomerTransaction.setRemark(transactions.getRemark());
				Double dbalance = depositcustomer.getBalance() - transactions.getAmount();
				depositCustomerTransaction.setBalance(dbalance);
				depositCustomerTransaction = transactionsDAO.save(depositCustomerTransaction);
				depositcustomer.setBalance(dbalance);
				depositcustomer = customerDAO.save(depositcustomer);
				
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

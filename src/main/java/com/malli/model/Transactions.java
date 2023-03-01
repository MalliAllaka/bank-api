package com.malli.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transactions")
public class Transactions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name = "`date`")
	private Date date;
	
	@Column(name = "`type`")
	private String type;
	
	@Column(name = "method")
	private String method;
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "`from`")
	private String from;
	
	@Column(name = "remark")
	private String remark;
	
	@Transient
	private long depositCustomerId;
	
	@Column(name = "balance")
	private Double balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getDepositCustomerId() {
		return depositCustomerId;
	}

	public void setDepositCustomerId(long depositCustomerId) {
		this.depositCustomerId = depositCustomerId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}



package com.cabway.services;

import java.util.List;

import com.cabway.exceptions.CustomerException;
import com.cabway.model.Customer;

public interface CustomerService {

	public Customer insertCustomer(Customer customer) throws CustomerException;
	
	public Customer updateCustomer(Customer customer) throws CustomerException;
	
	public Customer deleteCustomer(Integer customerId) throws CustomerException;
	
	public List<Customer> getAllCustomers() throws CustomerException;
	
	public Customer getCustomerById(Integer customerId) throws CustomerException;
	
	public Customer validateCustomer(String username, String password) throws CustomerException;
}

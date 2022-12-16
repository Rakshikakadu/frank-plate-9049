package com.cabway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.CustomerValidationDTO;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.CustomerDAO;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDAO customerDao;
	
	@Autowired
	private CurrentSessionDAO sessionDao; 
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public Customer insertCustomer(Customer customer) throws CustomerException {
		// TODO Auto-generated method stub
		
		Customer existingCustomer = customerDao.findByUserName(customer.getUserName());
		
		if(existingCustomer==null) {
			Customer registeredCustomer = customerDao.save(customer);
			
			return registeredCustomer;
		}else {
			throw new CustomerException("Username already exists. Please try with another username.");
		}
	}

	@Override
	public Customer updateCustomer(Customer customer, String key) throws CustomerException {
		
		CurrentSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser==null) {
			throw new CustomerException("Please provide a valid key to update your details.");
		}
	
		if(loggedInUser.getUserId() == customer.getCustomerId()) {
			
			return customerDao.save(customer);
		}else {
			throw new CustomerException("Invalid details. Please login first to update your details.");
		}
	}

	@Override
	public Customer deleteCustomer(Integer customerId, String key) throws CustomerException, LoginException {
		
		CurrentSession loggedInUser = sessionDao.findByUuid(key); 
		
		if(loggedInUser==null)
			throw new LoginException("Please log in to delete account.");
		
		if(customerDao.findById(loggedInUser.getUserId()).isPresent() ) {
			if(customerId == loggedInUser.getUserId()) {
				
				Customer existingCustomer =  customerDao.findById(customerId).orElseThrow(()-> new CustomerException("Invalid customerId."));
				
				sessionDao.delete(loggedInUser);
				
				customerDao.delete(existingCustomer);
				
				return existingCustomer;
			}else {
				throw new CustomerException("Invalid customer Id.");
			}
		}else if(adminDao.findById(loggedInUser.getUserId()).isPresent()) {
			
			Customer existingCustomer =  customerDao.findById(customerId).orElseThrow(()-> new CustomerException("Invalid customerId."));
			
			sessionDao.delete(loggedInUser);
			
			customerDao.delete(existingCustomer);
			
			return existingCustomer;
			
		}else {
			throw new IllegalArgumentException("Invalid user Id entered.");
		}
		
	}

	@Override
	public List<Customer> getAllCustomers(String key) throws CustomerException, LoginException {
		
		CurrentSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser != null) {
			List<Customer> customers = customerDao.findAll();
			
			if(customers.size()==0) {
				throw new CustomerException("No customers registered yet..");
			}else {
				return customers;
			}
		}else {
			throw new LoginException("Please login first.");
		}
	}

	@Override
	public Customer getCustomerById(Integer customerId, String key) throws CustomerException, LoginException {
		
		CurrentSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser==null)
			throw new LoginException("Please log in to get details.");
		
		if(customerDao.findById(loggedInUser.getUserId()).isPresent() ) {
			if(customerId == loggedInUser.getUserId()) {
				
				Customer existingCustomer =  customerDao.findById(customerId).orElseThrow(()-> new CustomerException("Invalid customerId."));
				return existingCustomer;
			}else {
				throw new CustomerException("Invalid customer Id.");
			}
		}else if(adminDao.findById(loggedInUser.getUserId()).isPresent()) {
			
			Customer existingCustomer =  customerDao.findById(customerId).orElseThrow(()-> new CustomerException("Invalid customerId."));
			return existingCustomer;
			
		}else {
			throw new IllegalArgumentException("Invalid user Id entered.");
		}
		
	}

	@Override
	public Customer validateCustomer(String username, String password) throws CustomerException {
		CustomerValidationDTO customerDto = customerDao.findCustomerUserNamePassword(username, password);
		
		if(customerDto==null)
			throw new CustomerException("Invalid details.");
		
		else {
			
			Customer customer = customerDao.findByUserName(username);
			
			return customer;
			
		}
	}

	

}

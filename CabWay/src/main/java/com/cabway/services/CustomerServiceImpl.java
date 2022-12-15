package com.cabway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.model.Customer;
import com.cabway.repository.CustomerDAO;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerDAO customerDao;
	
	@Override
	public Customer insertCustomer(Customer customer) throws CustomerException {
		// TODO Auto-generated method stub
		Customer registeredCustomer = customerDao.save(customer);
		
		return registeredCustomer;
	}

	@Override
	public Customer updateCustomer(Customer customer) throws CustomerException {
	
		Optional<Customer> opt = customerDao.findById(customer.getCustomerId());
		
		if(opt.isPresent()) {
			Customer existingCustomer = opt.get();
			existingCustomer.setUserName(customer.getUserName());
			existingCustomer.setPassword(customer.getPassword());
			existingCustomer.setAddress(customer.getAddress());
			existingCustomer.setEmail(customer.getEmail());
			existingCustomer.setMobileNo(customer.getMobileNo());
			
			customerDao.save(existingCustomer);
			
			return existingCustomer;
		}else {
			throw new CustomerException("Invalid details..");
		}
	}

	@Override
	public Customer deleteCustomer(Integer customerId) throws CustomerException {
		
		Optional<Customer> opt = customerDao.findById(customerId);
		
		if(opt.isPresent()) {
			Customer deletedCustomer = opt.get();
			
			customerDao.delete(deletedCustomer);
			
			return deletedCustomer;
		}else {
			throw new CustomerException("No customer found with id " + customerId);
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws CustomerException {
		
		List<Customer> customers = customerDao.findAll();
		
		if(customers.size()==0) {
			throw new CustomerException("No customers registered yet..");
		}else {
			return customers;
		}
	}

	@Override
	public Customer getCustomerById(Integer customerId) throws CustomerException {
		
		Optional<Customer> opt = customerDao.findById(customerId);
		
		if(opt.isPresent()) {
			Customer foundCustomer = opt.get();
			
			return foundCustomer;
		}else {
			throw new CustomerException("No customer found with id " + customerId);
		}
	}

	@Override
	public Customer validateCustomer(String username, String password) throws CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

}

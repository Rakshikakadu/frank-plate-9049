package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cabway.model.Customer;
import com.cabway.model.CustomerValidationDTO;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer>{

	public Customer findByUserName(String userName);
	
	@Query("select new com.cabway.model.CustomerValidationDTO(c.userName,c.password) from Customer c")
	public CustomerValidationDTO findCustomerUserNamePassword(String userName, String password);
}

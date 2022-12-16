package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabway.model.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer>{

	public Customer findByUserName(String userName);
}

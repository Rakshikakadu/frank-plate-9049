package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabway.model.Driver;

public interface DriverDAO extends JpaRepository<Driver, Integer>{

	public Driver findByUserName(String userName);
}

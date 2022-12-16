package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabway.model.Driver;

@Repository
public interface DriverDAO extends JpaRepository<Driver, Integer>{

	public Driver findByUserName(String userName);

}

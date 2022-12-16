package com.cabway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.DriverException;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.DriverDAO;


@Service
public class DriverServicesImpl implements DriverServices{

	@Autowired
	DriverDAO driverDao;
	
	@Autowired
	CurrentSessionDAO currentSessionDao;
	
	@Override
	public Driver insertDriver(Driver driver) throws DriverException {
		
		Driver existingDriver= driverDao.findByUserName(driver.getUserName());
		
		if(existingDriver != null) 
			throw new CustomerException("Driver Already Registered with this ID");
			
			return driverDao.save(driver);
			
	}

	@Override
	public Driver updateDriver(Driver driver,String key) throws DriverException {
		CurrentSession loggedInUser= currentSessionDao.findByUuid(key);
		
		
		if(loggedInUser == null) {
			throw new DriverException("Please provide a valid key to update a driver");
		}
		
		
		if(driver.getDriverId() == loggedInUser.getUserId()) {
			
			return driverDao.save(driver);
		}
		else
			throw new DriverException("Invalid Driver details, please login first");
		
		
	}

	@Override
	public Driver deleteDriver(Integer driverId,String key) throws DriverException {
		
		
		CurrentSession loggedInUser= currentSessionDao.findByUuid(key);
		
		
		if(loggedInUser == null) {
			throw new DriverException("Please provide a valid key to delete a driver");
		}
		
		
		if(driverId == loggedInUser.getUserId()) {
			Driver deletedDriver=driverDao.findById(driverId).orElseThrow(()->new DriverException("driver not found"));
			
			driverDao.delete(deletedDriver);
			
			return deletedDriver;
		}
		else
			throw new DriverException("Invalid Driver details, please login first");
		
	
	}

	@Override
	public List<Driver> viewBestDriver(String key) throws DriverException {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		return null;
	}

	@Override
	public Driver viewDriver(Integer driverId,String key) throws DriverException {
		CurrentSession loggedInUser= currentSessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new DriverException("Please provide a valid key to view a driver");
		}
		
		
		if(driverId == loggedInUser.getUserId()) {
			Driver viewDriver=driverDao.findById(driverId).orElseThrow(()->new DriverException("driver not found"));
			
			
			return viewDriver;
		}
		else
			throw new DriverException("Invalid Driver details, please login first");
	
	}

}

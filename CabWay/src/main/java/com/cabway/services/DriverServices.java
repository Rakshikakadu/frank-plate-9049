package com.cabway.services;

import java.util.List;

import com.cabway.exceptions.DriverException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.Driver;

public interface DriverServices {
	public Driver insertDriver(Driver driver) throws DriverException;

	public Driver updateDriver(Driver driver,String key) throws DriverException;

	public Driver deleteDriver(Integer driverID,String key) throws DriverException;

	public List<Driver> viewBestDriver(String key) throws DriverException;

	public Driver viewDriver(Integer driverId,String key) throws DriverException, LoginException ;
	
	public Driver rateDriverByCustomer(Driver driver,String key)throws DriverException, LoginException;
}

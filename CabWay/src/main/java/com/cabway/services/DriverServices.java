package com.cabway.services;

import java.util.List;

import com.cabway.exceptions.DriverException;
import com.cabway.model.Driver;

public interface DriverServices {
	public Driver insertDriver(Driver driver) throws DriverException;

	public Driver updateDriver(Driver driver) throws DriverException;

	public Driver deleteDriver(int driverID) throws DriverException;

	public List<Driver> viewBestDriver() throws DriverException;

	public Driver viewDriver(int driverId) throws DriverException;
}

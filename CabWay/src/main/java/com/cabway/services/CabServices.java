package com.cabway.services;

import java.util.List;

import com.cabway.exceptions.DriverException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.Cab;

public interface CabServices {

	public Cab updateCab(Cab cab) throws DriverException,LoginException;
	
	public List<Cab> viewCabsOfType(String carType) throws DriverException,LoginException;
	
	public int countCabsOfType(String carType) throws DriverException, LoginException;
}

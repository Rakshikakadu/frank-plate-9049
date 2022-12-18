package com.cabway.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.DriverException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.DriverDAO;

@Service
public class DriverServicesImpl implements DriverServices {

	@Autowired
	DriverDAO driverDao;

	@Autowired
	CurrentSessionDAO currentSessionDao;

	@Autowired
	private AdminDao adminDao;

	@Override
	public Driver insertDriver(Driver driver) throws DriverException {

		Driver existingDriver = driverDao.findByUserName(driver.getUserName());

		if (existingDriver != null)
			throw new CustomerException("Driver Already Registered with user ID");

		return driverDao.save(driver);

	}

	@Override
	public Driver updateDriver(Driver driver, String key) throws DriverException {
		CurrentSession loggedInUser = currentSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new DriverException("Please provide a valid key to update a driver");
		}

		if (driver.getDriverId() == loggedInUser.getUserId()) {

			return driverDao.save(driver);
		} else
			throw new DriverException("Invalid Driver details, please login first");

	}

	@Override
	public Driver deleteDriver(Integer driverId, String key) throws DriverException {

		CurrentSession loggedInUser = currentSessionDao.findByUuid(key);

		if (driverDao.findById(loggedInUser.getUserId()).isPresent()) {
			if (driverId == loggedInUser.getUserId()) {

				Driver existingDriver = driverDao.findById(driverId)
						.orElseThrow(() -> new DriverException("Invalid driverId."));

				currentSessionDao.delete(loggedInUser);

				driverDao.delete(existingDriver);

				return existingDriver;
			} else {
				throw new DriverException("Invalid Id.");
			}
		} else if (adminDao.findById(loggedInUser.getUserId()).isPresent()) {
			Driver existingDriver = driverDao.findById(driverId)
					.orElseThrow(() -> new DriverException("Invalid driverId."));

			currentSessionDao.delete(loggedInUser);

			driverDao.delete(existingDriver);

			return existingDriver;
		} else {
			throw new IllegalArgumentException("Invalid user Id entered.");
		}

	}

	@Override
	public List<Driver> viewBestDriver(String key) throws DriverException {
		
		CurrentSession adminValidate = currentSessionDao.findByUuid(key);

		if (adminValidate == null) {
			throw new DriverException("Please provide a valid key to update a driver");
		}else {
			
			List<Driver> alldrivers = driverDao.findAll();
			
			List<Driver> drivers = new ArrayList<>();
			
			for(Driver driver : alldrivers) {
				
				if(driver.getRating() >= 4.5) {
					
					drivers.add(driver);
					
				}
				
			}
			
			if(!drivers.isEmpty()) {
				
				return drivers;
			}else {
				throw new DriverException("No drivers found for rating above 4.5 ");
			}
			
		}
		
	}

	@Override
	public Driver viewDriver(Integer driverId, String key) throws DriverException, LoginException {
		CurrentSession loggedInUser = currentSessionDao.findByUuid(key);

		if (loggedInUser == null)
			throw new LoginException("Please log in to get details.");

		if (driverDao.findById(loggedInUser.getUserId()).isPresent()) {
			if (driverId == loggedInUser.getUserId()) {

				Driver existingDriver = driverDao.findById(driverId)
						.orElseThrow(() -> new DriverException("Invalid driverId."));
				return existingDriver;
			} else {
				throw new CustomerException("Invalid customer Id.");
			}
		} else if (adminDao.findById(loggedInUser.getUserId()).isPresent()) {

			Driver existingDriver = driverDao.findById(driverId)
					.orElseThrow(() -> new DriverException("Invalid driverId."));
			return existingDriver;

		} else {
			throw new IllegalArgumentException("Invalid user Id entered.");
		}

	}

	@Override
	public Driver rateDriverByCustomer(Driver driver, String key) throws DriverException, LoginException {

		CurrentSession loggedInUser = currentSessionDao.findByUuid(key);

		if (loggedInUser == null)
			throw new LoginException("Please log in to get details.");
		else {

			Driver ratedriver = driverDao.findById(driver.getDriverId())
					.orElseThrow(() -> new DriverException("No driver found"));

			if (ratedriver.getRating() != 0) {

				Float newRating = (driver.getRating() + ratedriver.getRating()) / 2;

				ratedriver.setRating(newRating);

				return driverDao.save(ratedriver);

			} else {

				ratedriver.setRating(driver.getRating());

				return driverDao.save(ratedriver);

			}

		}

	}

}

package com.cabway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cabway.exceptions.DriverException;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.services.DriverServices;

@RestController
public class DriverController {
	@Autowired
	DriverServices driverService;
	
	@PostMapping("/driver")
	public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver){
		
		Driver registeredDriver  = driverService.insertDriver(driver);
		
		return new ResponseEntity<Driver>(registeredDriver, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/updateDriver")
	public ResponseEntity<Driver> updateCustomer(@RequestBody Driver driver, @RequestParam String key){
		
		Driver updatedDriver = driverService.updateDriver(driver, key);
		
		return new ResponseEntity<Driver>(updatedDriver, HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/deleteDriver/{driverId}")
	public ResponseEntity<Driver> deleteCustomer(@PathVariable("driverId") Integer driverId, @RequestParam String key) throws DriverException{
		
		Driver deletedCustomer = driverService.deleteDriver(driverId, key);
		
		return new ResponseEntity<Driver>(deletedCustomer, HttpStatus.OK);
		
	}
}

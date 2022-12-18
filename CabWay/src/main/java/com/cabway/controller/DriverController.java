package com.cabway.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.DriverException;
import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.Cab;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.model.TripBooking;
import com.cabway.services.CabServices;
import com.cabway.services.DriverServices;
import com.cabway.services.TripBookingService;

@RestController
public class DriverController {
	@Autowired
	private DriverServices driverService;
	
	@Autowired
	private TripBookingService tbService;
	
	@Autowired
	private CabServices cabService;
	
	@PostMapping("/driver")
	public ResponseEntity<Driver> registerDriver(@Valid @RequestBody Driver driver){
		
		Driver registeredDriver  = driverService.insertDriver(driver);
		
		return new ResponseEntity<Driver>(registeredDriver, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateDriver")
	public ResponseEntity<Driver> updateDriver(@Valid @RequestBody Driver driver, @RequestParam String key){
		
		Driver updatedDriver = driverService.updateDriver(driver, key);
		
		return new ResponseEntity<Driver>(updatedDriver, HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/deleteDriver/{driverId}")
	public ResponseEntity<Driver> deleteDriver(@PathVariable("driverId") Integer driverId, @RequestParam String key) throws DriverException{
		
		Driver deletedDriver = driverService.deleteDriver(driverId, key);
		
		return new ResponseEntity<Driver>(deletedDriver, HttpStatus.OK);
		
	}
	
	@GetMapping("/driver/{driverId}")
	public ResponseEntity<Driver> viewDriver(@PathVariable("driverId") Integer driverId, @RequestParam String key) throws DriverException, LoginException{
		
		Driver driver = driverService.viewDriver(driverId, key);
		
		return new ResponseEntity<Driver>(driver, HttpStatus.OK);
		
	}
	
	@PutMapping("/driver/updateTripBooking/{userId}")
	public ResponseEntity<TripBooking> updateTrip(@PathVariable("userId") Integer userId, @RequestParam String key,@Valid @RequestBody TripBooking tripBook) throws TripBookinException, LoginException, AdminException{
		
		TripBooking trip = tbService.updateTripBooking(tripBook, userId, key);
		
		return new ResponseEntity<TripBooking>(trip, HttpStatus.ACCEPTED);
		
	}
	

	@PutMapping("/drivers/updateCab")
	public ResponseEntity<Cab> updateCab(@Valid @RequestBody Cab cab, @RequestParam String key) throws DriverException, LoginException{
		
		Cab updatedCab = cabService.updateCab(cab);
		
		return new ResponseEntity<Cab>(updatedCab, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/driver/tripbooking/bill/{customerid}")
	public ResponseEntity<TripBooking> calculateBillHandler(@PathVariable("customerid") Integer customerid, @RequestParam String key) throws DriverException, LoginException, CustomerException, TripBookinException, AdminException{
		
		TripBooking trip = tbService.calculateBill(customerid, key);
		
		return new ResponseEntity<TripBooking>(trip, HttpStatus.OK);
		
	}

}

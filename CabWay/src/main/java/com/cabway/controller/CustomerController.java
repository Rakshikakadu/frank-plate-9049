package com.cabway.controller;

import java.util.List;
import java.util.Set;

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
import com.cabway.model.Customer;
import com.cabway.model.CustomerValidationDTO;
import com.cabway.model.Driver;
import com.cabway.model.TripBooking;
import com.cabway.services.CustomerService;
import com.cabway.services.DriverServices;
import com.cabway.services.TripBookingService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	private TripBookingService tripbookService;
	
	@Autowired
	private DriverServices driverService;
	
	@PostMapping("/customer")
	public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer){
		
		Customer registeredCustomer  = customerService.insertCustomer(customer);
		
		return new ResponseEntity<Customer>(registeredCustomer, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, @RequestParam String key){
		
		Customer updatedCustomer = customerService.updateCustomer(customer, key);
		
		return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/deleteCustomer/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer customerId, @RequestParam String key) throws CustomerException, LoginException{
		
		Customer deletedCustomer = customerService.deleteCustomer(customerId, key);
		
		return new ResponseEntity<Customer>(deletedCustomer, HttpStatus.OK);
		
	}
	
//	@GetMapping("/customers")
//	public ResponseEntity<List<Customer>> getAllCustomer(@RequestParam String key) throws CustomerException, LoginException{
//		
//		List<Customer> customers = customerService.getAllCustomers(key);
//		
//		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
//	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId, @RequestParam String key) throws CustomerException, LoginException{
		
		Customer customer = customerService.getCustomerById(customerId, key);
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		
	}
	
	@PostMapping("/customers/tripBook/{cid}")
	public ResponseEntity<TripBooking> insertTrip(@PathVariable("cid") Integer customerId ,@Valid @RequestBody TripBooking tripBook, @RequestParam String key) throws TripBookinException, LoginException{
		
		TripBooking tripBook1 = tripbookService.insertTripBooking(tripBook, customerId, key);
		
		
		return new ResponseEntity<TripBooking>(tripBook1, HttpStatus.OK);
	}
	
	@GetMapping("/validateCustomer")
	public ResponseEntity<Customer> validateCustomer(@Valid @RequestBody CustomerValidationDTO customerDto){
		
		String userName = customerDto.getUserName();
		String password = customerDto.getPassword();
		
		Customer customer = customerService.validateCustomer(userName, password);
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@PutMapping("/customer/updateTripBooking/{userId}")
	public ResponseEntity<TripBooking> updateTrip(@PathVariable("userId") Integer userId, @RequestParam String key,@Valid @RequestBody TripBooking tripBook) throws TripBookinException, LoginException, AdminException{
		
		TripBooking trip = tripbookService.updateTripBooking(tripBook, userId, key);
		
		return new ResponseEntity<TripBooking>(trip, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/customer/tripbookings/{cid}")
	public ResponseEntity<Set<TripBooking>> getAllTripsOfCustomers(@PathVariable("cid") Integer customerId,  @RequestParam String key) throws CustomerException, AdminException, TripBookinException, LoginException{
		
		Set<TripBooking> customerTrips = tripbookService.viewAllTripsOfCustomerById(customerId, key);
		
		
		return new ResponseEntity<Set<TripBooking>>(customerTrips, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/customer/ratedriver")
	public ResponseEntity<Driver> rateDriver(@Valid @RequestBody Driver driver, @RequestParam String key) throws DriverException, LoginException{
		
		Driver ratedriver = driverService.rateDriverByCustomer(driver, key);
		
		return new ResponseEntity<Driver>(ratedriver, HttpStatus.ACCEPTED);
	}
}

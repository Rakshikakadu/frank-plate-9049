package com.cabway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.Customer;
import com.cabway.model.TripBooking;
import com.cabway.services.CustomerService;
import com.cabway.services.TripBookingService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	private TripBookingService tripbookService;
	
	@PostMapping("/customer")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
		
		Customer registeredCustomer  = customerService.insertCustomer(customer);
		
		return new ResponseEntity<Customer>(registeredCustomer, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/updateCustomer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
		
		Customer updatedCustomer = customerService.updateCustomer(customer);
		
		return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/deleteCustomer/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer customerId){
		
		Customer deletedCustomer = customerService.deleteCustomer(customerId);
		
		return new ResponseEntity<Customer>(deletedCustomer, HttpStatus.OK);
		
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomer(){
		
		List<Customer> customers = customerService.getAllCustomers();
		
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId){
		
		Customer customer = customerService.getCustomerById(customerId);
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		
	}
	
	@PostMapping("/customers/tripBook")
	public ResponseEntity<TripBooking> insertTrip(@RequestBody TripBooking tripBook,@RequestParam String key) throws TripBookinException, LoginException{
		
		TripBooking tripBook1 = tripbookService.insertTripBooking(tripBook, key);
		
		
		return new ResponseEntity<TripBooking>(tripBook1, HttpStatus.OK);
	}
}

package com.cabway.controller;

import java.util.List;

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
import com.cabway.exceptions.LoginException;
import com.cabway.model.Admin;
import com.cabway.model.Customer;
import com.cabway.services.AdminService;
import com.cabway.services.CustomerService;
import com.cabway.services.TripBookingService;

@RestController
//@RequestMapping("/adminService")
public class AdminController {

	@Autowired
	private AdminService aService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TripBookingService tbService;

	@PostMapping("/admins")
	public ResponseEntity<Admin> insertAdmin(@RequestBody Admin admin) throws AdminException {

		Admin ad = aService.insertAdmin(admin);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}

	@PutMapping("/admins")
	public ResponseEntity<Admin> updatedAdmin(@RequestBody Admin admin ,@RequestParam String key) throws AdminException {

		Admin ad = aService.updateAdminDetails(admin, key);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}
	
	@DeleteMapping("/admins/{aid}")
	public ResponseEntity<Admin> deletedAdmin(@PathVariable("aid") Integer adminId,@RequestParam String key) throws AdminException {

		Admin ad = aService.deleteAdminDetails(adminId, key);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/admins/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam String key) throws CustomerException, LoginException{
		
		List<Customer> customers = customerService.getAllCustomers(key);
		
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
		
	}

}

package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cabway.exceptions.AdminException;
import com.cabway.model.Admin;
import com.masai.services.AdminService;

@RestController
//@RequestMapping("/adminService")
public class AdminController {
	
	@Autowired
	private AdminService aService;
	
	@PostMapping("/admins")
	public ResponseEntity<Admin> insertAdmin(@RequestBody Admin admin) throws AdminException{
		
		Admin ad =  aService.insertAdmin(admin);
		
		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);
		
	}
	
}

package com.cabway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cabway.exceptions.LoginException;
import com.cabway.model.LoginDTO;
import com.cabway.services.LoginServices;

@RestController
public class LoginController {

	@Autowired
	private LoginServices login;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDTO dto) throws LoginException{
		
		String res = login.logIntoAccount(dto);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
		
	}
	
	@PostMapping("/logout")
	public String logoutCustomer(@RequestParam(required = false) String key) throws LoginException {
		
		return login.logOutFromAccount(key);
		
	}
}
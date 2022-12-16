package com.cabway.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.LoginException;
import com.cabway.model.Admin;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.model.LoginDTO;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.CustomerDAO;
import com.cabway.repository.DriverDAO;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServicesImpl implements LoginServices{

	@Autowired
	private CurrentSessionDAO sessionDao;
	
	@Autowired
	private CustomerDAO customerDao;
	
	@Autowired
	private AdminDao admindao;
	
	@Autowired
	private DriverDAO driverDao;
	
	
	@Override
	public String logIntoAccount(LoginDTO dto) throws LoginException {
		
		if(dto.getUserType().equals("customer")) {
			Customer existingCustomer = customerDao.findByUserName(dto.getUserName());
			
			if(existingCustomer == null)
				throw new LoginException("Please enter a valid username..");
			
			else {
				Optional<CurrentSession> validUserSessionOpt = sessionDao.findById(existingCustomer.getCustomerId());
				
				if(validUserSessionOpt.isPresent()) {
					throw new LoginException("User already logged in..");
				}
				
				if(existingCustomer.getPassword().equals(dto.getPassword())) {
					
					String key = RandomString.make(6);
					
					CurrentSession userSession = new CurrentSession(existingCustomer.getCustomerId(), key, LocalDateTime.now());
					
					sessionDao.save(userSession);
					
					return userSession.toString();
					
				}else {
					throw new LoginException("Please enter a valid password..");
				}
				
			}
		}else if(dto.getUserType().equals("admin")){
			Admin existingAdmin = admindao.findByUserName(dto.getUserName());
			
			if(existingAdmin == null)
				throw new LoginException("Please enter a valid username..");
			
			else {
				Optional<CurrentSession> validUserSessionOpt = sessionDao.findById(existingAdmin.getAdminId());
				
				if(validUserSessionOpt.isPresent()) {
					throw new LoginException("User already logged in..");
				}
				
				if(existingAdmin.getPassword().equals(dto.getPassword())) {
					
					String key = RandomString.make(6);
					
					CurrentSession userSession = new CurrentSession(existingAdmin.getAdminId(), key, LocalDateTime.now());
					
					sessionDao.save(userSession);
					
					return userSession.toString();
					
				}else {
					throw new LoginException("Please enter a valid password..");
				}
				
			}
		}
		else if(dto.getUserType().equals("driver")){
			Driver existingDriver = driverDao.findByUserName(dto.getUserName());
			
			if(existingDriver == null)
				throw new LoginException("Please enter a valid username..");
			
			else {
				Optional<CurrentSession> validUserSessionOpt = sessionDao.findById(existingDriver.getDriverId());
				
				if(validUserSessionOpt.isPresent()) {
					throw new LoginException("User already logged in..");
				}
				
				if(existingDriver.getPassword().equals(dto.getPassword())) {
					
					String key = RandomString.make(6);
					
					CurrentSession userSession = new CurrentSession(existingDriver.getDriverId(), key, LocalDateTime.now());
					
					sessionDao.save(userSession);
					
					return userSession.toString();
					
				}else {
					throw new LoginException("Please enter a valid password..");
				}
				
			}
		}else {
			throw new LoginException("Usertype should be admin or customer or driver..");
		}
	}

	@Override
	public String logOutFromAccount(String key) throws LoginException {
		
		CurrentSession validSession = sessionDao.findByUuid(key);
		
		if(validSession==null) {
			throw new LoginException("No user logged in with key " + key);
		}else {
			
			sessionDao.delete(validSession);
			
			return "Logged Out!";
		}
	}

}

package com.cabway.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.LoginDTO;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.CustomerDAO;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServicesImpl implements LoginServices{

	@Autowired
	private CurrentSessionDAO sessionDao;
	
	@Autowired
	private CustomerDAO customerDao;
	
	
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
		}else {
			throw new CustomerException("No customer found");
		}
//		return null;
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

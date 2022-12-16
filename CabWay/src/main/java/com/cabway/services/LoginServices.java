package com.cabway.services;

import com.cabway.exceptions.LoginException;
import com.cabway.model.LoginDTO;

public interface LoginServices {

	public String logIntoAccount(LoginDTO dto)throws LoginException;

	public String logOutFromAccount(String key)throws LoginException;
}

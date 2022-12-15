package com.masai.services;

import com.cabway.exceptions.AdminException;
import com.cabway.model.Admin;

public interface AdminService {
	
	public Admin insertAdmin(Admin admin)throws AdminException;
	
}

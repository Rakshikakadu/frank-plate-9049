package com.cabway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.AdminException;
import com.cabway.model.Admin;
import com.cabway.repository.AdminDao;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDao aDao;

	@Override
	public Admin insertAdmin(Admin admin) throws AdminException {
		
		Admin saveadmin = aDao.save(admin);
		
//		if(saveadmin!=null) {
//			return saveadmin;
//		}else {
//			throw new AdminException("Admin not found");
//		}
		return saveadmin;
		
	}
	
	

	
	
	
	
}

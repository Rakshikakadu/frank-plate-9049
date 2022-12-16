package com.cabway.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.AdminException;
import com.cabway.model.Admin;
import com.cabway.model.CurrentSession;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao aDao;

	@Autowired
	private CurrentSessionDAO csDao;

	@Override
	public Admin insertAdmin(Admin admin) throws AdminException {

		Admin existingAdmin = aDao.findByUserName(admin.getUserName());

		if (existingAdmin != null) {
			throw new AdminException("Admin already present with username: " + admin.getUserName());
		} else {
			Admin saveadmin = aDao.save(admin);
			return saveadmin;
		}
	}

	@Override
	public Admin updateAdminDetails(Admin admin, String key) throws AdminException {

		CurrentSession adminLogin = csDao.findByUuid(key);

		if (adminLogin == null) {
			throw new AdminException("Please provide a valid key to update a Admin");
		}

		if (admin.getAdminId() == adminLogin.getUserId()) {
			return aDao.save(admin);
		} else {
			throw new AdminException("Invalid Admin details ,please login first");
		}

	}

	@Override
	public Admin deleteAdminDetails(Integer adminId,String key) throws AdminException {
		
		CurrentSession adminLogin = csDao.findByUuid(key);
		
		if(adminLogin==null) {
			throw new AdminException("Please provide a valid key to delete a Admin"); 
		}
		
		if(adminId==adminLogin.getUserId()) {
			Admin existingAdmin = aDao.findById(adminId).orElseThrow(() -> new AdminException("Admin not found"));
			aDao.delete(existingAdmin);

			return existingAdmin;
		} else {
			throw new AdminException("Invalid Admin details ,please login first");
		}
		
	}
	
	
	

}

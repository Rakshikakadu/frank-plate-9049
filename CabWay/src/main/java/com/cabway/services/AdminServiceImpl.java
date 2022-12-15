package com.cabway.services;

import java.util.Optional;

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

	@Override
	public Admin updateAdminDetails(Admin admin) throws AdminException {
		
		Optional<Admin> aOpt = aDao.findById(admin.getAdminId());
		
		if(aOpt.isPresent()) {
			Admin prevAdmin = aOpt.get();
			
			Admin updatedAdmin = aDao.save(prevAdmin);
			return updatedAdmin;
		}else {
			throw new AdminException("Admin not found");
		}
		
	}

	@Override
	public Admin deleteAdminDetails(Integer adminId) throws AdminException {
		
		Admin existingAdmin = aDao.findById(adminId).orElseThrow(() -> new AdminException("Admin not found"));
		aDao.delete(existingAdmin);

		return existingAdmin;
		
	}
	
	

	
	
	
	
}

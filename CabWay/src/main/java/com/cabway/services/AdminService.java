package com.cabway.services;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.model.Admin;


public interface AdminService {
	
	public Admin insertAdmin(Admin admin)throws AdminException;
	
	public Admin updateAdminDetails(Admin admin,String key)throws AdminException;
	
	public Admin deleteAdminDetails(Integer adminId, String key)throws AdminException;
	
	//public List<TripBooking> getAllTripsByCustomerId(Integer customerId)throws CustomerException;
	
	//public List<TripBooking> getTripsCabwise()throws CabException;
	
	//public List<TripBooking> getTripsCustomerwise()throws CabException,CustomerException;
	
	//public List<TripBooking> getTripsDatewise()throws CabException;
	
	//public List<TripBooking> getAllTripsForDays(Integer customerId,)
}

package com.cabway.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.Admin;
import com.cabway.model.TripBooking;


public interface AdminService {
	
	public Admin insertAdmin(Admin admin)throws AdminException;
	
	public Admin updateAdminDetails(Admin admin,String key)throws AdminException;
	
	public Admin deleteAdminDetails(Integer adminId, String key)throws AdminException;
	
	public Set<TripBooking> getAllTripsOfCustomers(String key)throws CustomerException,AdminException;
	
	//public List<TripBooking> getTripsCabwise()throws CabException;
	
	public Set<TripBooking> getTripsByCustomerId(Integer customerId,String key)throws CustomerException,TripBookinException,AdminException;
	
	public Set<TripBooking> getTripsDatewise(Date date,String key)throws CustomerException,TripBookinException,AdminException;
	
	//public Set<TripBooking> getAllTripsForDays(Integer customerId,LocalDateTime fromDate, LocalDateTime toDate, String key)throws CustomerException,TripBookinException,AdminException;

	Set<TripBooking> getAllTripsForDays(Integer customerId, LocalDate fromDate, LocalDate toDate, String key)
			throws CustomerException, TripBookinException, AdminException;
	
	

	
}

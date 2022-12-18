package com.cabway.services;


import java.util.Set;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.TripBooking;

public interface TripBookingService {
	
	public TripBooking insertTripBooking(TripBooking tripBook, Integer customerId, String key)throws TripBookinException, LoginException;
	
	public TripBooking updateTripBooking(TripBooking tripBook,Integer userId, String key)throws TripBookinException, LoginException, AdminException;
	
	public TripBooking deleteTripBooking(Integer tbId,Integer userId, String key)throws TripBookinException, LoginException, AdminException;
	
	public Set<TripBooking> viewAllTripsOfCustomerById(Integer customerId,String key)throws TripBookinException,CustomerException, LoginException;
	
	public TripBooking calculateBill(Integer tripBookingId, String key)throws TripBookinException,CustomerException, LoginException, AdminException;
	
//	public TripBooking calculateBill(Integer customerId, String key)throws TripBookinException,CustomerException, LoginException, AdminException;
	
	
}

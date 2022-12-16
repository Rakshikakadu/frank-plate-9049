package com.cabway.services;

import java.util.List;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.TripBooking;

public interface TripBookingService {
	
	public TripBooking insertTripBooking(TripBooking tripBook, String key)throws TripBookinException, LoginException;
	
	public TripBooking updateTripBooking(TripBooking tripBook,Integer userId, String key)throws TripBookinException;
	
	public TripBooking deleteTripBooking(Integer tbId,Integer userId, String key)throws TripBookinException;
	
	public List<TripBooking> viewAllTripsOfCustomerById(Integer customerId,String key)throws TripBookinException,CustomerException;
	
	public TripBooking calculateBill(Integer customerId, String key)throws TripBookinException,CustomerException;
	
	
}

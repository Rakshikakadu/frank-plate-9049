package com.cabway.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
import com.cabway.model.Admin;
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.TripBooking;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.CustomerDAO;
import com.cabway.repository.TripBookingDao;

@Service
public class TripBookingServiceImpl implements TripBookingService {

	@Autowired
	private CustomerDAO cDao;

	@Autowired
	private AdminDao aDao;

	@Autowired
	private CurrentSessionDAO csDao;

	@Autowired
	private TripBookingDao tbDao;

	@Override
	public TripBooking insertTripBooking(TripBooking tripBook, String key) throws TripBookinException, LoginException {

		CurrentSession customerValidate = csDao.findByUuid(key);

		if (customerValidate == null) {
			throw new LoginException("Please login first to book a trip");
		} else {

			if (customerValidate.getUserId() == tripBook.getCustomerId()) {

				if (tripBook.getFromDateTime().isAfter(LocalDateTime.now())
						&& tripBook.getToDateTime().isAfter(tripBook.getFromDateTime())) {

					tripBook.setStatus("Booked");
					
					Customer customer = cDao.findById(customerValidate.getUserId()).orElseThrow(()-> new CustomerException("Invalid customer id"));
					
					customer.getTripBookings().add(tripBook);
					
					cDao.save(customer);
					
					return tbDao.save(tripBook);

				} else {
					throw new TripBookinException("From Date cannot be before current date");
				}

			} else {
				throw new TripBookinException("Invalid tripBooking details");
			}

		}

	}

	@Override
	public TripBooking updateTripBooking(TripBooking tripBook, Integer userId, String key)throws TripBookinException, LoginException, AdminException {

		CurrentSession customerValidate = csDao.findByUuid(key);

		if (customerValidate == null) {
			throw new LoginException("Please login first to book a trip");
		}

		return null;
	}

	@Override
	public TripBooking deleteTripBooking(Integer tbId, Integer userId, String key)
			throws TripBookinException, LoginException, AdminException {

		CurrentSession adminValidate = csDao.findByUuid(key);

		if (adminValidate == null) {
			throw new LoginException("Please login first to book a trip");
		}else {
			
			
			if(userId== adminValidate.getUserId()) {
				
				TripBooking tripbook = tbDao.findById(tbId).orElseThrow(() -> new TripBookinException("Trip not found id"));
				
				if(tripbook.getStatus().equalsIgnoreCase("Cancelled") || tripbook.getStatus().equalsIgnoreCase("Completed")) {
					
					tbDao.delete(tripbook);
					return tripbook;
					
				}else {
					throw new TripBookinException("Trip is not completed or cancelled");
				}
			}else {
				throw new AdminException("Invalid Admin id"+ userId);
			}
			
		}

		
	}

	@Override
	public List<TripBooking> viewAllTripsOfCustomerById(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TripBooking calculateBill(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException {
		// TODO Auto-generated method stub
		return null;
	}

}

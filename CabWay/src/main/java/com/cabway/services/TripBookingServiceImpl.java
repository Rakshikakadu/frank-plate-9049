package com.cabway.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.LoginException;
import com.cabway.exceptions.TripBookinException;
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

				if (tripBook.getFromDateTime().isAfter(LocalDateTime.now()) && tripBook.getToDateTime().isAfter(tripBook.getFromDateTime())) {
					
					
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
	public TripBooking updateTripBooking(TripBooking tripBook, Integer userId, String key) throws TripBookinException {

		return null;
	}

	@Override
	public TripBooking deleteTripBooking(Integer tbId, Integer userId, String key) throws TripBookinException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TripBooking> viewAllTripsOfCustomerById(Integer customerId, String key)
			throws TripBookinException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TripBooking calculateBill(Integer customerId, String key) throws TripBookinException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

}

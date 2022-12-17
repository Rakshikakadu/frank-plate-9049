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
import com.cabway.model.CurrentSession;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.model.TripBooking;
import com.cabway.repository.AdminDao;
import com.cabway.repository.CurrentSessionDAO;
import com.cabway.repository.CustomerDAO;
import com.cabway.repository.DriverDAO;
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
	
	@Autowired
	private DriverDAO driverDao;

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
					
//					cDao.save(customer);
					
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

		CurrentSession userValidate = csDao.findByUuid(key);

		if (userValidate == null) {
			throw new LoginException("Please login first to book a trip");
		}
		
		if(cDao.findById(userId).isPresent()) {
			
			TripBooking updatedTripBooking = null;
			
			if(userId==userValidate.getUserId()) {
				
				Customer customer = cDao.findById(userId).orElseThrow(()-> new CustomerException("Invalid user Id."));
				
				Set<TripBooking> trips =  customer.getTripBookings();
				
				for(TripBooking trip : trips) {
					if(trip.getTripBookingId() == tripBook.getTripBookingId()) {
						if(tripBook.getFromDateTime().isAfter(LocalDateTime.now()) && tripBook.getToDateTime().isAfter(tripBook.getFromDateTime())) {
							
							trip.setFromLocation(tripBook.getFromLocation());
							trip.setToLocation(tripBook.getToLocation());
							trip.setFromDateTime(tripBook.getFromDateTime());
							trip.setToDateTime(tripBook.getToDateTime());
							trip.setStatus(tripBook.getStatus());
							trip.setDistanceInKm(tripBook.getDistanceInKm());
							
							updatedTripBooking = trip;
							
							tbDao.save(updatedTripBooking);
						}
					}
				}
//				
//				cDao.save(customer);
//				TripBooking existingTb = tbDao.findById(tripBook.getTripBookingId()).orElseThrow(()-> new TripBookinException("Invalid tripBookig id."));
//				
//				Driver driver = existingTb.getDriver();
//				
//				if(driver!=null) {
//					for(TripBooking trip : driver.getTripBookings()) {
//						if(tripBook.getTripBookingId()== trip.getTripBookingId()) {
//							trip.setFromLocation(tripBook.getFromLocation());
//							trip.setToLocation(tripBook.getToLocation());
//							trip.setFromDateTime(tripBook.getFromDateTime());
//							trip.setToDateTime(tripBook.getToDateTime());
//							trip.setStatus(tripBook.getStatus());
//						}
//					}
//					driverDao.save(driver);
//				}
			}
			if(updatedTripBooking==null)
				throw new TripBookinException("Invalid tripBooking details.");
			
			else
				return updatedTripBooking;
			
		}else if(aDao.findById(userId).isPresent()) {
			
			TripBooking updatedTripBooking = null;
			
			if(userId==userValidate.getUserId()) {
				
				updatedTripBooking = tbDao.findById(tripBook.getTripBookingId()).orElseThrow(()-> new TripBookinException("Invalid tripBooking details."));
				
				updatedTripBooking.setDriver(tripBook.getDriver());
				
				updatedTripBooking.setStatus(tripBook.getStatus());
				
				
				tbDao.save(updatedTripBooking);
				
//				Customer customer = cDao.findById(tripBook.getCustomerId()).orElseThrow(()-> new CustomerException("Invalid user Id."));
				
//				Set<TripBooking> trips =  customer.getTripBookings();
				
//				List<TripBooking> trips = tbDao.findAll();
				
//				for(TripBooking trip : trips) {
//					if(trip.getTripBookingId() == tripBook.getTripBookingId()) {
////						if(tripBook.getFromDateTime().isAfter(LocalDateTime.now()) && tripBook.getToDateTime().isAfter(tripBook.getFromDateTime())) {
//							
//							Driver assignedDriver = tripBook.getDriver();
//							trip.setDriver(assignedDriver);
//							trip.setStatus(tripBook.getStatus());
//							
////							Driver driver = driverDao.findById(tripBook.getDriver().getDriverId()).orElseThrow(()-> new TripBookinException("Invalid tripBooking details."));
////							
////							driver.getTripBookings().add(tripBook);
////							
//							updatedTripBooking = trip;
////							
////							driverDao.save(driver);
//							
////						}
//					}
//				}
//				tbDao.save(updatedTripBooking);
//				cDao.save(customer);

			}
			if(updatedTripBooking==null)
				throw new TripBookinException("Invalid tripBooking details.");
			
			else
				return updatedTripBooking;
			
		}else {
			throw new IllegalArgumentException("Invalid user id.");
		}

		
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
	public Set<TripBooking> viewAllTripsOfCustomerById(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException {
		CurrentSession customerValidate = csDao.findByUuid(key);

		if (customerValidate == null) {
			throw new LoginException("Please login first to get a trip details");
		}else {
			
			
			if(customerId == customerValidate.getUserId()) {
				
				Customer customer = cDao.findById(customerId).orElseThrow(() -> new CustomerException("Customer not found id"));
				Set<TripBooking> alltrips = customer.getTripBookings();
				
				return alltrips;
				 
				
			}else {
				throw new CustomerException("Invalid customer id :"+customerId);
			}
			
		}
		
	}

	@Override
	public TripBooking calculateBill(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException, AdminException {
		
		CurrentSession adminValidate = csDao.findByUuid(key);

		if (adminValidate == null) {
			throw new LoginException("Please login first to calculate bill for customerid : "+ customerId);
		}else {
			
			
			if(customerId== adminValidate.getUserId()) {
				
				//Customer customer = cDao.findById(customerId).orElseThrow(() -> new TripBookinException("Trip not found id"));
				
				TripBooking tripbooking = tbDao.findByCustomerId(customerId);
				
				if(tripbooking!=null) {
					
					Float updatedBill = tripbooking.getDriver().getCab().getPerKmRate() * tripbooking.getDistanceInKm();
					
					
					tripbooking.setBill(updatedBill);
					
					tbDao.save(tripbooking);
				}else {
					throw new TripBookinException("No trips for given customerId: "+customerId);
				}
				
				
			}else {
				throw new AdminException("Invalid Customer id"+ customerId);
			}
			
		}
		
		return null;
	}

}

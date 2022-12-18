package com.cabway.services;

import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
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
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao aDao;

	@Autowired
	private CurrentSessionDAO csDao;

	@Autowired
	private CustomerDAO cDao;
	
	@Autowired
	private TripBookingDao tbDao;

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
	public Admin deleteAdminDetails(Integer adminId, String key) throws AdminException {

		CurrentSession adminLogin = csDao.findByUuid(key);

		if (adminLogin == null) {
			throw new AdminException("Please provide a valid key to delete a Admin");
		}

		if (adminId == adminLogin.getUserId()) {
			Admin existingAdmin = aDao.findById(adminId).orElseThrow(() -> new AdminException("Admin not found"));
			aDao.delete(existingAdmin);

			return existingAdmin;
		} else {
			throw new AdminException("Invalid Admin details ,please login first");
		}

	}

	@Override
	public List<TripBooking> getAllTripsOfCustomers(String key) throws CustomerException, AdminException {

		CurrentSession adminLogin = csDao.findByUuid(key);

		if (adminLogin == null) {
			throw new AdminException("Please provide a valid key to get all trips of customers");
		} else {
			List<TripBooking> customerTrips = tbDao.findAll();

			if (customerTrips.size() > 0) {

//				Set<TripBooking> customerTrips = new HashSet<>();
//
//				for (Customer c : customers) {
//
//					customerTrips.addAll(c.getTripBookings());
//
//				}
				
				return customerTrips;
			} else {
				throw new CustomerException("No trip is present.");
			}

		}

	}

	@Override
	public List<TripBooking> getTripsByCustomerId(Integer customerId, String key) throws CustomerException, TripBookinException, AdminException {
		
		CurrentSession adminLogin = csDao.findByUuid(key);

		if (adminLogin == null) {
			throw new AdminException("Please provide a valid key to get all trips of customers");
		}else {
			
			Customer c = cDao.findById(customerId).orElseThrow(() -> new CustomerException("Customer not find with cid :"+customerId));
			
			Set<TripBooking> customertrips = c.getTripBookings();
			
			List<TripBooking> customerTrips = new ArrayList<>(customertrips);
			
			if(customerTrips.size()>0) {
				return customerTrips;
			}else {
				throw new TripBookinException("No trips found for customer "+ customerId);
			}
			
			
		}
		
	}

//	@Override
//	public Set<TripBooking> getTripsDatewise(Date date, String key)throws CustomerException, TripBookinException, AdminException {
//		
//		CurrentSession adminLogin = csDao.findByUuid(key);
//
//		if (adminLogin == null) {
//			throw new AdminException("Please provide a valid key to get all trips of customers");
//		}else {
//			
//			List<Customer> customers = cDao.findAll();
//			
//			if(customers.size()>0) {
//				
//				Set<TripBooking> dateWiseTrips = new HashSet<>();
//				
//				for(Customer c : customers) {
//					
//					Set<TripBooking> trips = c.getTripBookings();
//					
//					for(TripBooking tb : trips) {
//						
//						Instant instant1 = tb.getFromDateTime().atZone(ZoneId.systemDefault()).toInstant();
//						Date startdate = Date.from(instant1);
//						
//						if(startdate == date) {
//							dateWiseTrips.add(tb);
//						}
//						
//					}
//					
//				}
//				
//			}else {
//				throw new CustomerException("No one customer is present");
//			}
//			
//		}
//		
//		
//		return null;
//	}
//
//	@Override
//	public Set<TripBooking> getAllTripsForDays(Integer customerId, LocalDate fromDate, LocalDate toDate, String key)throws CustomerException, TripBookinException, AdminException {
//		
//		CurrentSession adminLogin = csDao.findByUuid(key);
//
//		if (adminLogin == null) {
//			throw new AdminException("Please provide a valid key to get all trips of customers");
//		}else {
//			
//			List<Customer> customers = cDao.findAll();
//			
//			if(customers.size()>0) {
//				
//				Set<TripBooking> getAllTrips = new HashSet<>();
//				
//				for(Customer c : customers) {
//					
//					Set<TripBooking> trips = c.getTripBookings();
//					
//					for(TripBooking tb : trips) {
//						
//						Instant instant1 = tb.getFromDateTime().atZone(ZoneId.systemDefault()).toInstant();
//						LocalDate startdate = LocalDate.from(instant1);
//						
//						Instant instant2 = tb.getFromDateTime().atZone(ZoneId.systemDefault()).toInstant();
//						LocalDate enddate = LocalDate.from(instant2);
//						
//						if(startdate.isEqual(toDate) && enddate.isEqual(toDate)) {
//							
//							getAllTrips.add(tb);
//							
//						}
//						
//					}
//					
//				}
//				
//			}else {
//				throw new CustomerException("No one customer is present");
//			}
//			
//		}
//		
//		return null;
//	}

	

	

}

package com.cabway.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabway.exceptions.AdminException;
import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.DriverException;
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
	public TripBooking insertTripBooking(TripBooking tripBook, Integer customerId, String key)
			throws TripBookinException, LoginException {

		CurrentSession customerValidate = csDao.findByUuid(key);

		if (customerValidate == null) {
			throw new LoginException("Please login first to book a trip");
		} else {

			if (customerValidate.getUserId() == customerId && cDao.findById(customerId).isPresent()) {

				if (tripBook.getFromDateTime().isAfter(LocalDateTime.now())
						&& tripBook.getToDateTime().isAfter(tripBook.getFromDateTime())) {

					Customer customer = cDao.findById(customerId)
							.orElseThrow(() -> new CustomerException("Invalid customer id"));


					tripBook.setStatus("Booked");

					tripBook.setCustomer(customer);

					tbDao.save(tripBook);
					
					customer.getTripBookings().add(tripBook);
					
					cDao.save(customer);

					return tripBook;
				} else {
					throw new TripBookinException("From Date cannot be before current date");
				}

			} else {
				throw new TripBookinException("Invalid booking details.");
			}

		}

	}

	@Override
	public TripBooking updateTripBooking(TripBooking tripBook, Integer userId, String key)
			throws TripBookinException, LoginException, AdminException {

		CurrentSession userValidate = csDao.findByUuid(key);

		if (userValidate == null) {
			throw new LoginException("Please login first to book a trip");
		}

		if (cDao.findById(userId).isPresent()) {

			TripBooking trip = tbDao.findById(tripBook.getTripBookingId())
					.orElseThrow(() -> new TripBookinException("Invalid tripBookingId."));

			Customer customer = cDao.findById(userId).orElseThrow(() -> new CustomerException("Invalid customer id."));

			Set<TripBooking> trips = customer.getTripBookings();
			for (TripBooking updateTrip : trips) {
				if (updateTrip.getTripBookingId() == trip.getTripBookingId()) {
					trip.setFromDateTime(tripBook.getFromDateTime());
					trip.setToDateTime(tripBook.getToDateTime());
					trip.setFromLocation(tripBook.getFromLocation());
					trip.setToLocation(tripBook.getToLocation());
					trip.setDistanceInKm(tripBook.getDistanceInKm());
					trip.setStatus(tripBook.getStatus());

					updateTrip.setFromDateTime(tripBook.getFromDateTime());
					updateTrip.setToDateTime(tripBook.getToDateTime());
					updateTrip.setFromLocation(tripBook.getFromLocation());
					updateTrip.setToLocation(tripBook.getToLocation());
					updateTrip.setDistanceInKm(tripBook.getDistanceInKm());
					updateTrip.setStatus(tripBook.getStatus());

					cDao.save(customer);
					tbDao.save(trip);
				}
			}
			return trip;

		} else if (aDao.findById(userId).isPresent()) {

			TripBooking trip = tbDao.findById(tripBook.getTripBookingId())
					.orElseThrow(() -> new TripBookinException("Invalid tripBookingId."));

			Integer assignDriverId = tripBook.getDriver().getDriverId();

			Driver driver = driverDao.findById(assignDriverId)
					.orElseThrow(() -> new DriverException("Invalid driver id."));

			trip.setDriver(driver);

			trip.setStatus("assigned");

			for (TripBooking updateTrip : driver.getTripBookings()) {
				if (updateTrip.getTripBookingId() == tripBook.getTripBookingId()) {
					updateTrip.setDriver(driver);

					updateTrip.setStatus("assigned");
				}
			}

			Customer customer = trip.getCustomer();
			for (TripBooking updateTrip : customer.getTripBookings()) {
				if (updateTrip.getTripBookingId() == tripBook.getTripBookingId()) {
					updateTrip.setDriver(driver);

					updateTrip.setStatus("assigned");
				}
			}
			driverDao.save(driver);
			cDao.save(customer);
			return tbDao.save(trip);

		} else if (driverDao.findById(userId).isPresent()) {

			TripBooking trip = tbDao.findById(tripBook.getTripBookingId())
					.orElseThrow(() -> new TripBookinException("Invalid tripBookingId."));

			Driver driver = driverDao.findById(userId).orElseThrow(() -> new DriverException("Invalid driver id."));

			trip.setStatus(tripBook.getStatus());

			for (TripBooking updateTrip : driver.getTripBookings()) {
				if (updateTrip.getTripBookingId() == tripBook.getTripBookingId()) {

					updateTrip.setStatus(tripBook.getStatus());
				}
			}

			Customer customer = trip.getCustomer();
			for (TripBooking updateTrip : customer.getTripBookings()) {
				if (updateTrip.getTripBookingId() == tripBook.getTripBookingId()) {

					updateTrip.setStatus(tripBook.getStatus());
				}
			}
			driverDao.save(driver);
			cDao.save(customer);

			return tbDao.save(trip);

		}

		throw new TripBookinException("invalid details");
	}

	@Override
	public TripBooking deleteTripBooking(Integer tbId, Integer userId, String key)
			throws TripBookinException, LoginException, AdminException {

		CurrentSession adminValidate = csDao.findByUuid(key);

		if (adminValidate == null) {
			throw new LoginException("Please login first to book a trip");
		} else {

			if (userId == adminValidate.getUserId()) {

				TripBooking tripbook = tbDao.findById(tbId)
						.orElseThrow(() -> new TripBookinException("Trip not found id"));

				if (tripbook.getStatus().equalsIgnoreCase("Cancelled")
						|| tripbook.getStatus().equalsIgnoreCase("Completed")) {

					Customer customer = tripbook.getCustomer();

					customer.getTripBookings().remove(tripbook);

					if (tripbook.getDriver() != null) {

						Driver driver = tripbook.getDriver();

						driver.getTripBookings().remove(tripbook);

						driverDao.save(driver);

					}

					cDao.save(customer);

					tbDao.delete(tripbook);

					return tripbook;

				} else {
					throw new TripBookinException("Trip is not completed or cancelled");
				}
			} else {
				throw new AdminException("Invalid Admin id" + userId);
			}

		}

	}

	@Override
	public Set<TripBooking> viewAllTripsOfCustomerById(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException {
		CurrentSession customerValidate = csDao.findByUuid(key);

		if (customerValidate == null) {
			throw new LoginException("Please login first to get a trip details");
		} else {

			if (customerId == customerValidate.getUserId()) {

				Customer customer = cDao.findById(customerId)
						.orElseThrow(() -> new CustomerException("Customer not found id"));

				Set<TripBooking> alltrips = customer.getTripBookings();

				return alltrips;

			} else {
				throw new CustomerException("Invalid customer id :" + customerId);
			}

		}

	}

	@Override
	public TripBooking calculateBill(Integer customerId, String key)
			throws TripBookinException, CustomerException, LoginException, AdminException {

		CurrentSession userValidate = csDao.findByUuid(key);

		if (userValidate == null) {
			throw new LoginException("Please login first to calculate bill for customerid : " + customerId);
		} else {

			Customer customer = cDao.findById(customerId)
					.orElseThrow(() -> new CustomerException("customer not found."));

			Set<TripBooking> trips = customer.getTripBookings();

			if (!trips.isEmpty()) {
				for (TripBooking trip : trips) {

					if (trip.getStatus().equals("completed") && trip.getBill() == 0) {

						Float updateBill = trip.getDriver().getCab().getPerKmRate() * trip.getDistanceInKm();

						trip.setBill(updateBill);

						TripBooking updatedTrip = tbDao.save(trip);
						cDao.save(customer);
						return updatedTrip;
					}

				}

				throw new TripBookinException("Trip not completed yet or Bill already generated.");

			} else {
				throw new TripBookinException("Trips not found for customerId: " + customerId);
			}
			
		}

	}
}

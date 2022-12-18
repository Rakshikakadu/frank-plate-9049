package com.cabway.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cabway.exceptions.AdminException;

import com.cabway.exceptions.CustomerException;
import com.cabway.exceptions.DriverException;
import com.cabway.exceptions.LoginException;
import com.cabway.model.Admin;
import com.cabway.model.Cab;
import com.cabway.model.Customer;
import com.cabway.model.Driver;
import com.cabway.services.AdminService;
import com.cabway.services.CabServices;
import com.cabway.services.CustomerService;
import com.cabway.services.DriverServices;
import com.cabway.services.TripBookingService;

import com.cabway.exceptions.TripBookinException;
import com.cabway.model.TripBooking;

@RestController
//@RequestMapping("/adminService")
public class AdminController {

	@Autowired
	private AdminService aService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TripBookingService tbService;
	

	
	@Autowired
	private CabServices cabService;

	@Autowired
	private DriverServices driverService;



	@PostMapping("/admins")
	public ResponseEntity<Admin> insertAdmin(@Valid @RequestBody Admin admin) throws AdminException {

		Admin ad = aService.insertAdmin(admin);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}

	@PutMapping("/admins")
	public ResponseEntity<Admin> updatedAdmin(@Valid @RequestBody Admin admin ,@RequestParam String key) throws AdminException {

		Admin ad = aService.updateAdminDetails(admin, key);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}
	
	@DeleteMapping("/admins/{aid}")
	public ResponseEntity<Admin> deletedAdmin(@PathVariable("aid") Integer adminId,@RequestParam String key) throws AdminException {

		Admin ad = aService.deleteAdminDetails(adminId, key);

		return new ResponseEntity<Admin>(ad, HttpStatus.ACCEPTED);

	}

	@GetMapping("/admins/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam String key) throws CustomerException, LoginException{
		
		List<Customer> customers = customerService.getAllCustomers(key);
		
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
		
	}
	
	@GetMapping("/admins/customer/{cid}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("cid") Integer customerId, @RequestParam String key) throws CustomerException, LoginException{
		
		Customer customer = customerService.getCustomerById(customerId, key);
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/admins/deletetripBooking/{tbid}/{uid}")
	public ResponseEntity<TripBooking> deletetripBooking(@PathVariable("tbid") Integer tbid,@PathVariable("uid") Integer uid,@RequestParam String key) throws TripBookinException, LoginException, AdminException{
		
		TripBooking tBooking = tbService.deleteTripBooking(tbid, uid, key);
		
		
		return new ResponseEntity<TripBooking>(tBooking, HttpStatus.ACCEPTED);
	}

	@PutMapping("/admin/updateTripBooking/{userId}")
	public ResponseEntity<TripBooking> updateTrip(@PathVariable("userId") Integer userId, @RequestParam String key, @Valid @RequestBody TripBooking tripBook) throws TripBookinException, LoginException, AdminException{
		
		TripBooking trip = tbService.updateTripBooking(tripBook, userId, key);
		
		return new ResponseEntity<TripBooking>(trip, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/admins/customers/tripbookings")
	public ResponseEntity<List<TripBooking>> getAllTrips(@RequestParam String key) throws CustomerException, AdminException{
		
		List<TripBooking> customersTrips = aService.getAllTripsOfCustomers(key);
		
		
		return new ResponseEntity<List<TripBooking>>(customersTrips, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/admins/customer/tripbookings/{cid}")
	public ResponseEntity<List<TripBooking>> getAllTripsOfCustomers(@PathVariable("cid") Integer customerId,  @RequestParam String key) throws CustomerException, AdminException, TripBookinException, LoginException{
		
		List<TripBooking> customerTrips = aService.getTripsByCustomerId(customerId, key);
		
		
		return new ResponseEntity<List<TripBooking>>(customerTrips, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/admins/tripbookings/datewise/{date}")
	public ResponseEntity<Set<TripBooking>> getTripsDateWise(@PathVariable("date") String date, String key) throws CustomerException, TripBookinException, AdminException{
		
		LocalDate d = LocalDate.parse(date);
		
		Set<TripBooking> tripsByDate = aService.getTripsDatewise(d, key);
		
		return new ResponseEntity<Set<TripBooking>>(tripsByDate, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/admins/tripbookings/datewise/{cid}/{sdate}/{edate}")
	public ResponseEntity<Set<TripBooking>> getAllTripsForDays(@PathVariable("cid") Integer customerId, @PathVariable("sdate") String sdate, @PathVariable("edate") String edate,String key) throws CustomerException, TripBookinException, AdminException{
		
		LocalDate startDate = LocalDate.parse(sdate);
		
		LocalDate endDate = LocalDate.parse(edate);
		
		Set<TripBooking> tripsByDate = aService.getAllTripsForDays(customerId, startDate, endDate, key);
				
		
		return new ResponseEntity<Set<TripBooking>>(tripsByDate, HttpStatus.ACCEPTED);
	}

	
	@GetMapping("/admins/bestdrivers")
	public ResponseEntity<List<Driver>> viewBestDriver(@RequestParam String key){
		
		List<Driver> bestDrivers = driverService.viewBestDriver(key);
		
		return new ResponseEntity<List<Driver>>(bestDrivers, HttpStatus.OK);
		
	}
	
	
	
	@PutMapping("/admins/tripbooking/bill/{cid}")
	public ResponseEntity<TripBooking> generateBill(@PathVariable("cid") Integer customerId, @RequestParam String key) throws CustomerException, TripBookinException, LoginException, AdminException{
		
		TripBooking trip = tbService.calculateBill(customerId, key);
		
		return new ResponseEntity<TripBooking>(trip, HttpStatus.ACCEPTED);
		
	}
	

	@GetMapping("/admins/viewCabByType/{carType}")
	public ResponseEntity<List<Cab>> viewCabsOfType(@PathVariable("carType") String carType, @RequestParam String key) throws DriverException, LoginException{
		
		List<Cab> cabs = cabService.viewCabsOfType(carType);
		
		return new ResponseEntity<List<Cab>>(cabs,HttpStatus.OK);
		
	}
	
	@GetMapping("/admins/countCabsOfType/{carType}")
	public ResponseEntity<Integer> countCabsOfType(@PathVariable("carType") String carType, @RequestParam String key) throws DriverException, LoginException{
		
		Integer count = cabService.countCabsOfType(carType);
		
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}

	
	
	@DeleteMapping("admins/deleteDriver/{driverId}")
	public ResponseEntity<Driver> deleteDriver(@PathVariable("driverId") Integer driverId, @RequestParam String key) throws DriverException{
		
		Driver deletedDriver = driverService.deleteDriver(driverId, key);
		
		return new ResponseEntity<Driver>(deletedDriver, HttpStatus.OK);
		
	}
	
	@GetMapping("admins/driver/{driverId}")
	public ResponseEntity<Driver> viewDriver(@PathVariable("driverId") Integer driverId, @RequestParam String key) throws DriverException, LoginException{
		
		Driver driver = driverService.viewDriver(driverId, key);
		
		return new ResponseEntity<Driver>(driver, HttpStatus.OK);

		
	}
}

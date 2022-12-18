package com.cabway.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabway.model.TripBooking;

@Repository
public interface TripBookingDao extends JpaRepository<TripBooking, Integer>{
	
	
//	public TripBooking findByCustomerId(Integer customerId);
	
//	public List<TripBooking> findByCustomerId(Integer customerId);
}

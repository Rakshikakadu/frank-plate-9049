package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabway.model.TripBooking;

@Repository
public interface TripBookingDao extends JpaRepository<TripBooking, Integer>{
	
	
	
}

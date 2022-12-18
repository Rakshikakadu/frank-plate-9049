package com.cabway.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TripBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tripBookingId;
	
//	private Integer customerId;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Driver driver;
	
	private String fromLocation;
	
	private String toLocation;
	
	private LocalDateTime fromDateTime;
	
	private LocalDateTime toDateTime;
	
	private String status;
	
	private Float distanceInKm;
	
	private Float bill;
	
	
}

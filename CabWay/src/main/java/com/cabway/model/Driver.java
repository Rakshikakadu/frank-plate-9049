package com.cabway.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver extends AbstractUser{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer driverId;
	private String licenseNo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cab cab;
	
	private Float rating;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "driver")
	private Set<TripBooking> tripBookings = new HashSet<>();
	
	public Driver(String userName, String password, String address, String mobileNo, String email, Integer driverId,
			String licenseNo, Cab cab, Float rating) {
		super(userName, password, address, mobileNo, email);
		this.driverId = driverId;
		this.licenseNo = licenseNo;
		this.cab = cab;
		this.rating = rating;
	}
	
	
	
}

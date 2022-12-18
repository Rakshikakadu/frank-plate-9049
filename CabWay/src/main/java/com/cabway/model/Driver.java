package com.cabway.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

//@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver extends AbstractUser{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer driverId;
	
//	@NotNull
	@Column(unique = true)
	private String licenseNo;
	
	@Embedded
	private Cab cab;
	
	@Max(value=5,message = "Rating can not be more than 5")
	@Min(value=0,message = "Rating can not be less than 0")
	private Float rating;
	
	@JsonIgnore
	@OneToMany(mappedBy = "driver")
	private Set<TripBooking> tripBookings = new HashSet<>();
	
	public Driver(String userName, String password, String address, String mobileNo, String email, Integer driverId,
			String licenseNo, Cab cab, Float rating) {
		super(userName, password, address, mobileNo, email);
		this.driverId = driverId;
		this.licenseNo = licenseNo;
		this.cab = cab;
		this.rating = rating;
	}

	public Driver(String userName, String password, String address, String mobileNo, String email, String licenseNo,
			Cab cab, Float rating) {
		super(userName, password, address, mobileNo, email);
		this.licenseNo = licenseNo;
		this.cab = cab;
		this.rating = rating;
	}
	
	
	
}

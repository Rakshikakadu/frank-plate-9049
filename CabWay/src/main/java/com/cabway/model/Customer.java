package com.cabway.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Customer extends AbstractUser{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private Set<TripBooking> tripBookings = new HashSet<>();

	public Customer(String userName, String password, String address, String mobileNo, String email,
			Integer customerId) {
		super(userName, password, address, mobileNo, email);
		this.customerId = customerId;
	}

	public Customer(String userName, String password, String address, String mobileNo, String email) {
		super(userName, password, address, mobileNo, email);
	}
	
	
	
	
}

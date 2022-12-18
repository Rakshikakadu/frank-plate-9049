package com.cabway.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	
//	@NotNull(message = "This field cannot be null")
	private String fromLocation;
	
//	@NotNull(message = "This field cannot be null")
	private String toLocation;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime fromDateTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime toDateTime;
	
	private String status;
	
//	@NotNull(message = "This field cannot be null")
	private Float distanceInKm;
	
	private Float bill;
	
	
}

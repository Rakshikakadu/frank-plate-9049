package com.cabway.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Cab {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cabId;
	private String carType;
	private Float perKmRate;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Driver driver;
}

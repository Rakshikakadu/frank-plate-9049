package com.cabway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cab {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cabId;
	private String carType;
	private Float perKmRate;
	
}

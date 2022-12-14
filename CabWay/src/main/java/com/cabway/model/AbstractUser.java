package com.cabway.model;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractUser {

	private String userName;
	private String password;
	private String address;
	private String mobileNo;
	private String email;
	

	
}

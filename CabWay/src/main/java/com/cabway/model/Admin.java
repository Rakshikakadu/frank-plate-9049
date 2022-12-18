package com.cabway.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Admin extends AbstractUser{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adminId;
	
	public Admin(String userName, String password, String address, String mobileNo, String email, Integer adminId) {
		super(userName, password, address, mobileNo, email);
		this.adminId = adminId;
	}

	public Admin(String userName, String password, String address, String mobileNo, String email) {
		super(userName, password, address, mobileNo, email);
	}
	
	
	
}

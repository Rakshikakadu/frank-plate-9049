package com.cabway.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends AbstractUser{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adminId;
	
	public Admin(String userName, String password, String address, String mobileNo, String email, Integer adminId) {
		super(userName, password, address, mobileNo, email);
		this.adminId = adminId;
	}
	
	public static void main(String[] args) {
		
		
		
	}
	
}

package com.cabway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends AbstractUser{

	private Integer adminId;
	
	public Admin(String userName, String password, String address, String mobileNo, String email, Integer adminId) {
		super(userName, password, address, mobileNo, email);
		this.adminId = adminId;
	}
	
	public static void main(String[] args) {
		
		
		
	}
	
}

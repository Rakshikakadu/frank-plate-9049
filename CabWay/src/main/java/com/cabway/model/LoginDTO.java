package com.cabway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO {

	private String userName;
	private String password;
	private String userType;
}

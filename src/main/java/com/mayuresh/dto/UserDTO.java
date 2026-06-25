package com.mayuresh.dto;

import com.mayuresh.entities.Role;

import lombok.Data;

@Data
public class UserDTO {
	private long userId;
	private String name;
	private String email;
	private String phone;
	private String profileImageName;
	private Role role;
}

package com.mayuresh.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mayuresh.entities.User;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.services.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ResponseWrapper> addUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseWrapper> loginUser(@RequestParam String username, @RequestParam String password)
	{
		return userService.loginUser(username, password);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseWrapper> getUserById(@PathVariable Long userId)
	{
		return userService.getUserById(userId);
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseWrapper> updateUser(@PathVariable Long userId, @RequestBody User user)
	{
		return userService.updateUser(userId, user);
	}

	@PutMapping("/users/{userId}/profile-image")
	public ResponseEntity<ResponseWrapper> updateProfileImage(@PathVariable Long userId, @RequestParam("profileImage") MultipartFile profileImage) throws IOException
	{
		return userService.updateProfileImage(userId, profileImage);
	}
}

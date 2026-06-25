package com.mayuresh.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mayuresh.dto.UserDTO;
import com.mayuresh.entities.User;
import com.mayuresh.entities.Role;
import com.mayuresh.repositiories.UserRepository;
import com.mayuresh.response_wrapper.ResponseWrapper;
import com.mayuresh.response_wrapper.UniversalResponse;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UniversalResponse response;

	private final String PROFILE_IMAGE_UPLOAD_DIR=System.getProperty("user.dir")+"/uploads/images";

	private final String ADMIN_EMAIL="mayureshpatil1@gmail.com";
	private final String ADMIN_PASSWORD="12345678";
	private final String EMAIL_PATTERN="^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
	private final String NAME_PATTERN="^[A-Za-z ]+$";

	public ResponseEntity<ResponseWrapper> addUser(User user)
	{
		if(user.getName()==null || user.getName().isBlank() ||
				user.getEmail()==null || user.getEmail().isBlank() ||
				user.getPhone()==null || user.getPhone().isBlank() ||
				user.getPassword()==null || user.getPassword().isBlank())
		{
			return response.send("Please fill all required fields", null, HttpStatus.BAD_REQUEST);
		}

		if(!user.getName().matches(NAME_PATTERN))
		{
			return response.send("Name should contain only alphabets", null, HttpStatus.BAD_REQUEST);
		}

		if(!user.getEmail().matches(EMAIL_PATTERN))
		{
			return response.send("Please enter a valid email", null, HttpStatus.BAD_REQUEST);
		}

		if(userRepository.findByEmail(user.getEmail()).isPresent())
		{
			return response.send("Email already registered", null, HttpStatus.BAD_REQUEST);
		}

		user.setRole(Role.COMMUTER);
		User savedUser=userRepository.save(user);
		return response.send("User registered", convertToDTO(savedUser), HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseWrapper> loginUser(String username, String password)
	{
		if(ADMIN_EMAIL.equals(username) && ADMIN_PASSWORD.equals(password))
		{
			return response.send("Admin login successful", getAdminDTO(), HttpStatus.OK);
		}

		Optional<User> existingUser=userRepository.findByEmail(username);
		if(existingUser.isPresent() && existingUser.get().getPassword().equals(password))
		{
			User user=existingUser.get();
			if(user.getRole()==null || user.getRole()==Role.ADMIN)
			{
				user.setRole(Role.COMMUTER);
				userRepository.save(user);
			}
			return response.send("Login successful", convertToDTO(user), HttpStatus.OK);
		}
		else
		{
			return response.send("Invalid username or password", null, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ResponseWrapper> getUserById(Long userId)
	{
		if(userId==0)
		{
			return response.send("Admin user found", getAdminDTO(), HttpStatus.OK);
		}

		Optional<User> existingUser=userRepository.findById(userId);
		if(existingUser.isPresent())
		{
			return response.send("User found", convertToDTO(existingUser.get()), HttpStatus.OK);
		}
		else
		{
			return response.send("User not found", null, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseWrapper> updateUser(Long userId, User user)
	{
		Optional<User> existingUser=userRepository.findById(userId);
		if(existingUser.isEmpty())
		{
			return response.send("User not found", null, HttpStatus.NOT_FOUND);
		}

		User savedUser=existingUser.get();
		if(user.getName()==null || !user.getName().matches(NAME_PATTERN))
		{
			return response.send("Name should contain only alphabets", null, HttpStatus.BAD_REQUEST);
		}
		if(user.getEmail()==null || !user.getEmail().matches(EMAIL_PATTERN))
		{
			return response.send("Please enter a valid email", null, HttpStatus.BAD_REQUEST);
		}

		savedUser.setName(user.getName());
		savedUser.setEmail(user.getEmail());
		savedUser.setPhone(user.getPhone());
		if(user.getPassword()!=null && !user.getPassword().isBlank())
		{
			savedUser.setPassword(user.getPassword());
		}
		savedUser=userRepository.save(savedUser);
		return response.send("User updated", convertToDTO(savedUser), HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper> updateProfileImage(Long userId, MultipartFile profileImage) throws IOException
	{
		Optional<User> existingUser=userRepository.findById(userId);
		if(existingUser.isEmpty())
		{
			return response.send("User not found", null, HttpStatus.NOT_FOUND);
		}

		Files.createDirectories(Paths.get(PROFILE_IMAGE_UPLOAD_DIR));

		User user=existingUser.get();
		String originalImageName=profileImage.getOriginalFilename();
		String imageName=userId+"_"+originalImageName;
		Path completeImagePath=Paths.get(PROFILE_IMAGE_UPLOAD_DIR, imageName);
		Files.write(completeImagePath, profileImage.getBytes());

		user.setProfileImageName(imageName);
		User savedUser=userRepository.save(user);
		return response.send("Profile image updated", convertToDTO(savedUser), HttpStatus.OK);
	}

	private UserDTO convertToDTO(User user)
	{
		UserDTO userDTO=new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhone(user.getPhone());
		userDTO.setProfileImageName(user.getProfileImageName());
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	private UserDTO getAdminDTO()
	{
		UserDTO adminDTO=new UserDTO();
		adminDTO.setUserId(0);
		adminDTO.setName("Mayuresh Admin");
		adminDTO.setEmail(ADMIN_EMAIL);
		adminDTO.setPhone("Admin account");
		adminDTO.setRole(Role.ADMIN);
		return adminDTO;
	}
}

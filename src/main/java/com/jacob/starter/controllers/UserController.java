package com.jacob.starter.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.starter.interfaces.UserRepository;
import com.jacob.starter.models.User;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById (
			@PathVariable(name = "id") Long userId) throws Exception {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new Exception("User not found: " + userId));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/users/{id}") 
	public ResponseEntity<User> updateUser(
			@PathVariable(value = "id") Long userId,
			@Valid @RequestBody User userDetails) throws Exception {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new Exception("User not found: " + userId));
		
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmailId(userDetails.getEmailId());
		user.setUpdatedBy(userDetails.getUpdatedBy());
		user.setUpdatedAt(new Date());
		
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteUser(
			@PathVariable(name = "id") Long userId) throws Exception {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new Exception("User not found: " + userId));
		
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", true);
		return response;
	}
}

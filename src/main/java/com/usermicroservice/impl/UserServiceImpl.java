package com.usermicroservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usermicroservice.entities.Rating;
import com.usermicroservice.entities.User;
import com.usermicroservice.exception.UserNotFoundException;
import com.usermicroservice.repositories.UserRepository;
import com.usermicroservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {

		// generate unique user id
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with given id is not found on server !!" + userId));

		ArrayList<Rating> forObject = restTemplate.getForObject("http://localhost:8084/users/" + user.getUserId(),
				ArrayList.class);

		logger.info("logger{}", forObject);
		return user;
	}

	@Override
	public User updateUserById(User user) {
		User existingUser = userRepository.findById(user.getUserId()).get();

		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setAbout(user.getAbout());
		User updatedUser = userRepository.save(existingUser);
		return updatedUser;
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

}

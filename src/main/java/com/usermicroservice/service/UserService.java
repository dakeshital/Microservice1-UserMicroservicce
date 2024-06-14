package com.usermicroservice.service;

import java.util.List;

import com.usermicroservice.entities.User;

public interface UserService {

	User saveUser(User user);

	List<User> getAllUsers();

	User getUserById(String userId);

	User updateUserById(User user);

	void deleteUser(String userId);
}

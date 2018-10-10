package com.kiranreddy.budgettracker.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	List<User> retrieveUsers() {
		return userRepository.findAll();
	}

	User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	User updateUser(String id, User inputUser) {
		Optional<User> optionalUser = userRepository.findById(id);
		User user = optionalUser.orElseThrow(() -> new UserNotFoundException("No User found with user id " + id));
		user.setFirstName(inputUser.getFirstName());
		user.setLastName(inputUser.getLastName());
		user.setEmail(inputUser.getEmail());
		return userRepository.save(user);
	}

	void deleteUser(User user) {
		userRepository.delete(user);
	}

	void deleteUser(String id) {
		User user = findUser(id);
		deleteUser(user);
	}

	User findUser(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException("No User found with user id " + id));
	}
}

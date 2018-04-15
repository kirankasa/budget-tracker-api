package com.kiranreddy.budgettracker.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> retrieveUsers() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User updateUser(Long id, User inputUser) {
		Optional<User> optionalUser = userRepository.findById(id);
		User user = optionalUser.orElseThrow(() -> new UserNotFoundException("No User found with user id " + id));
		user.setFirstName(inputUser.getFirstName());
		user.setLastName(inputUser.getLastName());
		user.setEmail(inputUser.getEmail());
		return userRepository.save(user);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public void deleteUser(Long id) {
		User user = findUser(id);
		deleteUser(user);
	}

	public User findUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException("No User found with user id " + id));
	}
}

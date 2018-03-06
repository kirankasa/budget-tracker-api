package com.kiranreddy.budgettracker.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> retrieveUsers() {
		return userService.retrieveUsers();
	}

	@GetMapping("/{id}")
	public User findUser(@PathVariable("id") Long id) {
		return userService.findUser(id);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

	@PostMapping
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(UserNotFoundException exception) {
		return exception.getMessage();
	}
}

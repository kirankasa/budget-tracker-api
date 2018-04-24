package com.kiranreddy.budgettracker.user;

import java.util.List;

import com.kiranreddy.budgettracker.category.TransactionCategory;
import com.kiranreddy.budgettracker.category.TransactionCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import com.kiranreddy.budgettracker.security.JwtTokenUtil;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private UserService userService;
	private TransactionCategoryService transactionCategoryService;

	public UserController(JwtTokenUtil jwtTokenUtil, UserDetailsService jwtUserDetailsService,
                          UserService userService, TransactionCategoryService transactionCategoryService) {
		this.userService = userService;
        this.transactionCategoryService = transactionCategoryService;
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

	@PostMapping("/register")
	public User saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
        transactionCategoryService.saveTransactionCategory(new TransactionCategory("Other",savedUser.getId()));
		return savedUser;
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

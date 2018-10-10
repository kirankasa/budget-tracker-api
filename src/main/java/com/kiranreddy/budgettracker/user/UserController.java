package com.kiranreddy.budgettracker.user;

import com.kiranreddy.budgettracker.category.TransactionCategory;
import com.kiranreddy.budgettracker.category.TransactionCategoryService;
import com.kiranreddy.budgettracker.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private UserService userService;
	private TransactionCategoryService transactionCategoryService;

	public UserController(UserService userService, TransactionCategoryService transactionCategoryService) {
		this.userService = userService;
        this.transactionCategoryService = transactionCategoryService;
    }

	@GetMapping
	public List<User> retrieveUsers() {
		return userService.retrieveUsers();
	}

	@GetMapping("/{id}")
	public User findUser(@PathVariable("id") String id) {
		return userService.findUser(id);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") String id) {
		userService.deleteUser(id);
	}

	@PostMapping("/register")
	public User saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		TransactionCategory transactionCategory = new TransactionCategory(null, "Other");
		transactionCategory.setUserId(savedUser.getId());
		transactionCategoryService.saveTransactionCategory(transactionCategory);
		return savedUser;
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") String id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(UserNotFoundException exception) {
		return exception.getMessage();
	}

}

package com.kiranreddy.budgettracker.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kiranreddy.budgettracker.security.JwtUser;

@RestController
@RequestMapping(value = "/transactions/categories")
public class TransactionCategoryController {

	private TransactionCategoryService transactionCategoryService;

	public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
		this.transactionCategoryService = transactionCategoryService;
	}

	@GetMapping
	public List<TransactionCategory> retrieveCategories(@AuthenticationPrincipal JwtUser user) {
		return transactionCategoryService.retrieveTransactionCategories(user.getId());
	}

	@GetMapping("/{id}")
	public TransactionCategory retrieveTransactionCategoryById(@PathVariable("id") Long id) {
		return transactionCategoryService.retrieveTransactionCategoryById(id);
	}

	@PostMapping
	public TransactionCategory saveTransactionCategory(@RequestBody TransactionCategory transactionCategory,
			@AuthenticationPrincipal JwtUser user) {
		transactionCategory.setUserId(user.getId());
		return transactionCategoryService.saveTransactionCategory(transactionCategory);
	}

	@PutMapping("/{id}")
	public TransactionCategory saveTransactionCategory(@PathVariable("id") Long id,
			@RequestBody TransactionCategory transactionCategory, @AuthenticationPrincipal JwtUser user) {
		transactionCategory.setUserId(user.getId());
		return transactionCategoryService.updateTransactionCategory(transactionCategory, id);
	}

	@ExceptionHandler(TransactionCategoryNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(TransactionCategoryNotFoundException transactionCategoryNotFoundException) {
		return transactionCategoryNotFoundException.getMessage();
	}

}

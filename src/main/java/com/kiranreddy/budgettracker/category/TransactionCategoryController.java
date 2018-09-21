package com.kiranreddy.budgettracker.category;

import com.kiranreddy.budgettracker.security.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public TransactionCategory retrieveTransactionCategoryById(@PathVariable("id") String id) {
		return transactionCategoryService.retrieveTransactionCategoryById(id);
	}

	@PostMapping
	public TransactionCategory saveTransactionCategory(@RequestBody TransactionCategory transactionCategory,
			@AuthenticationPrincipal JwtUser user) {
		transactionCategory.setUserId(user.getId());
		return transactionCategoryService.saveTransactionCategory(transactionCategory);
	}

	@PutMapping("/{id}")
	public TransactionCategory saveTransactionCategory(@PathVariable("id") String id,
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

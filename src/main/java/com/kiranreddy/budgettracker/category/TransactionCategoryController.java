package com.kiranreddy.budgettracker.category;

import com.kiranreddy.budgettracker.security.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions/categories")
public class TransactionCategoryController {

	private TransactionCategoryService transactionCategoryService;

	public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
		this.transactionCategoryService = transactionCategoryService;
	}

	@GetMapping
	public List<TransactionCategory> retrieveCategories(@AuthenticationPrincipal Authentication authentication) {
		return transactionCategoryService.retrieveTransactionCategories(authentication.getUserId());
	}

	@GetMapping("/{id}")
	public TransactionCategory retrieveTransactionCategoryById(@PathVariable("id") String id) {
		return transactionCategoryService.retrieveTransactionCategoryById(id);
	}

	@PostMapping
	public TransactionCategory saveTransactionCategory(@RequestBody TransactionCategory transactionCategory,
			@AuthenticationPrincipal Authentication authentication) {
		transactionCategory.setUserId(authentication.getUserId());
		return transactionCategoryService.saveTransactionCategory(transactionCategory);
	}

	@PutMapping("/{id}")
	public TransactionCategory saveTransactionCategory(@PathVariable("id") String id,
			@RequestBody TransactionCategory transactionCategory, @ApiIgnore @AuthenticationPrincipal Authentication authentication) {
		transactionCategory.setUserId(authentication.getUserId());
		return transactionCategoryService.updateTransactionCategory(transactionCategory, id);
	}

	@ExceptionHandler(TransactionCategoryNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(TransactionCategoryNotFoundException transactionCategoryNotFoundException) {
		return transactionCategoryNotFoundException.getMessage();
	}

}

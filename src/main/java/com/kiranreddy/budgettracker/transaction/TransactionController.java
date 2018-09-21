package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.security.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public List<Transaction> retrieveTransactions(@ApiIgnore @AuthenticationPrincipal JwtUser user) {
		return transactionService.retrieveTransactions(user.getId());
	}

	@GetMapping("/{id}")
	public Transaction retrieveTransaction(@PathVariable("id") String transactionId) {
		return transactionService.findTransaction(transactionId);
	}

	@PostMapping
	public Transaction saveTransaction(@RequestBody Transaction transaction, @ApiIgnore @AuthenticationPrincipal JwtUser user) {
		transaction.setUserId(user.getId());
		return transactionService.saveTransaction(transaction);
	}

	@PutMapping("/{id}")
	public Transaction updateTransaction(@PathVariable("id") String transactionId, @RequestBody Transaction transaction,
			@AuthenticationPrincipal JwtUser user) {
		transaction.setUserId(user.getId());
		return transactionService.updateTransaction(transactionId, transaction);
	}

	@DeleteMapping("/{id}")
	public void deleteTransaction(@PathVariable("id") String transactionId) {
		transactionService.deleteTransaction(transactionId);
	}

	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(TransactionNotFoundException exception) {
		return exception.getMessage();
	}
}

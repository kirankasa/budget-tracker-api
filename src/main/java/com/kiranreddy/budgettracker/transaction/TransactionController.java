package com.kiranreddy.budgettracker.transaction;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.kiranreddy.budgettracker.security.JwtUser;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public List<Transaction> retrieveTransactions(@AuthenticationPrincipal JwtUser user) {
		return transactionService.retrieveTransactions(user.getId());
	}

	@GetMapping("/{id}")
	public Transaction retrieveTransaction(@PathVariable("id") Long transactionId) {
		return transactionService.findTransaction(transactionId);
	}

	@PostMapping
	public Transaction saveTransaction(@RequestBody Transaction transaction, @AuthenticationPrincipal JwtUser user) {
		transaction.setUserId(user.getId());
		return transactionService.saveTransaction(transaction);
	}

	@PutMapping("/{id}")
	public Transaction updateTransaction(@PathVariable("id") Long transactionId, @RequestBody Transaction transaction,
			@AuthenticationPrincipal JwtUser user) {
		transaction.setUserId(user.getId());
		return transactionService.updateTransaction(transactionId, transaction);
	}

	@DeleteMapping("/{id}")
	public void deleteTransaction(@PathVariable("id") Long transactionId) {
		transactionService.deleteTransaction(transactionId);
	}

	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String exceptionHandler(TransactionNotFoundException exception) {
		return exception.getMessage();
	}
}

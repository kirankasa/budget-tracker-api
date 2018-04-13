package com.kiranreddy.budgettracker.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kiranreddy.budgettracker.user.UserNotFoundException;

@Service
public class TransactionService {

	private TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public List<Transaction> retrieveTransactions(Long userId) {
		return transactionRepository.findByUserId(userId);
	}

	public Transaction findTransaction(Long transactionId) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
		Transaction transaction = optionalTransaction
				.orElseThrow(() -> new UserNotFoundException("No Transaction found with  id " + transactionId));
		return transaction;
	}

	public void deleteTransaction(Transaction transaction) {
		transactionRepository.delete(transaction);
	}

	public void deleteTransaction(Long transactionId) {
		Transaction transaction = findTransaction(transactionId);
		deleteTransaction(transaction);
	}

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	public Transaction updateTransaction(Long transactionId, Transaction transactionInput) {
		findTransaction(transactionId);
		return transactionRepository.save(transactionInput);
	}

}

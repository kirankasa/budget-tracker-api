package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.security.Authentication;
import org.springframework.data.domain.Page;
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
    public Page<Transaction> retrieveTransactions(@ApiIgnore @AuthenticationPrincipal Authentication authentication,
                                                  @RequestParam(value = "page", defaultValue = "0") int offset,
                                                  @RequestParam(value = "size", defaultValue = "20") int limit) {
        return transactionService.retrieveTransactions(authentication.getUserId(), offset, limit);
    }

    @GetMapping("/{id}")
    public Transaction retrieveTransaction(@PathVariable("id") String transactionId) {
        return transactionService.findTransaction(transactionId);
    }

    @PostMapping
    public Transaction saveTransaction(@RequestBody Transaction transaction, @ApiIgnore @AuthenticationPrincipal Authentication authentication) {
        transaction.setUserId(authentication.getUserId());
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable("id") String transactionId, @RequestBody Transaction transaction,
                                         @AuthenticationPrincipal Authentication authentication) {
        transaction.setUserId(authentication.getUserId());
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

    @GetMapping("/amountPerCategory")
    public List<AmountPerCategory> retrieveAmountPerCategory(@ApiIgnore @AuthenticationPrincipal Authentication authentication,
                                                             @RequestParam("monthAndYear") String monthAndYear,
                                                             @RequestParam("transactionType") String transactionType) {
        return transactionService.getAmountPerCategory(authentication.getUserId(), monthAndYear, transactionType);
    }
}

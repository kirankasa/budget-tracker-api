package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.security.JwtUser;
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
    public Page<Transaction> retrieveTransactions(@ApiIgnore @AuthenticationPrincipal JwtUser user,
                                                  @RequestParam(value = "page", defaultValue = "0") int offset,
                                                  @RequestParam(value = "size", defaultValue = "20") int limit) {
        return transactionService.retrieveTransactions(user.getId(), offset, limit);
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

    @GetMapping("/amountPerCategory")
    public List<AmountPerCategory> retrieveAmountPerCategory(@ApiIgnore @AuthenticationPrincipal JwtUser user,
                                                             @RequestParam("monthAndYear") String monthAndYear,
                                                             @RequestParam("transactionType") String transactionType) {
        return transactionService.getAmountPerCategory(user.getId(), monthAndYear, transactionType);
    }
}

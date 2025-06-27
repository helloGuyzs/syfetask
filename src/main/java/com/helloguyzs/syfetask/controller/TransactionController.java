package com.helloguyzs.syfetask.controller;

import com.helloguyzs.syfetask.dto.transaction.*;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.helloguyzs.syfetask.utils.SessionUtil.getCurrentUserId;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")
    public GetTransactionReponse getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CategoryType type,
            HttpServletRequest request
    ) {
        Integer userId = getCurrentUserId(request);
        return transactionService.getAllTransaction(userId, startDate, endDate, category, type);
    }

    @PostMapping("/transactions")
    public CreateTransactionResponse addTransactions(@RequestBody @Valid CreateTransactionRequest request,
                                                     HttpServletRequest requestObj) {
        Integer userId = getCurrentUserId(requestObj);
        return transactionService.addTransaction(userId, request);
    }

    @PutMapping("/transactions/{id}")
    public CreateTransactionResponse updateTransaction(@PathVariable int id,
                                                       @RequestBody @Valid UpdateTransactionRequest request,
                                                       HttpServletRequest requestObj) {
        Integer userId = getCurrentUserId(requestObj);
        return transactionService.updateTransaction(userId, id, request);
    }

    @DeleteMapping("/transactions/{id}")
    public DeleteTransactionResponse deleteTransaction(@PathVariable Integer id,
                                                       HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        return transactionService.deleteTransaction(userId, id);
    }
}

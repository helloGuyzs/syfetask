package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.transaction.*;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
            @RequestParam(required = false) CategoryType type
    ) {

        Integer userId = 1 ;

        return transactionService.getAllTransaction( userId, startDate, endDate, category, type);
    }




    @PostMapping("/transactions")
    public CreateTransactionResponse addTransactions(@RequestBody @Valid CreateTransactionRequest request){
        Integer userId = 1 ;
        return transactionService.addTransaction(userId , request);
    }

    @PutMapping("/transactions/{id}")
    public CreateTransactionResponse updateTransaction(@PathVariable int id, @RequestBody @Valid UpdateTransactionRequest request){
        Integer userId = 1 ;
        return transactionService.updateTransaction( userId, id , request) ;
    }

    @DeleteMapping("/transactions/{id}")
    public DeleteTransactionResponse deleteTransaction(@PathVariable Integer id){

        Integer userId = 1 ;

        return transactionService.deleteTransaction( userId , id) ;
    }
}

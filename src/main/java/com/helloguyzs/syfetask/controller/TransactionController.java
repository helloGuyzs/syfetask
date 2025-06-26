package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.transaction.CreateTransactionRequest;
import com.helloguyzs.syfetask.dto.transaction.UpdateTransactionRequest;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(){

        return transactionService.getAllTransaction();
    }




    @PostMapping("/transactions")
    public String addTransactions( @RequestBody @Valid CreateTransactionRequest request){
        return transactionService.addTransaction(request);
    }

    @PutMapping("/transactions/{id}")
    public String updateTransaction(@PathVariable int id, @RequestBody @Valid UpdateTransactionRequest request){

        return transactionService.updateTransaction(id , request) ;
    }

    @DeleteMapping("/transactions/{id}")
    public String deleteTransaction(){

        return transactionService.deleteTransaction() ;
    }
}

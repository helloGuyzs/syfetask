package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.transaction.CreateTransactionRequest;
import com.helloguyzs.syfetask.dto.transaction.UpdateTransactionRequest;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo repo;


    public List< Transaction > getAllTransaction() {

        return repo.findAll();

    }

    public String addTransaction(CreateTransactionRequest requestDTO) {

        // need to make the check of category


        Transaction transaction = new Transaction();

        transaction.setUserId(1);
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDate(requestDTO.getDate());
        transaction.setCategory(requestDTO.getCategory());
        transaction.setDescription(requestDTO.getDescription());

        repo.save(transaction);



        return "Transaction Added successfully";
    }


    public String updateTransaction(int updateTransactoinID , UpdateTransactionRequest updateTransactionDTO) {

        Optional< Transaction > optTran = repo.findById(updateTransactoinID);

        if( optTran.isPresent()){

            Transaction transaction = optTran.get();

            if (updateTransactionDTO.getAmount() != null) {
                transaction.setAmount(updateTransactionDTO.getAmount());
            }

            if (updateTransactionDTO.getCategory() != null) {
                transaction.setCategory(updateTransactionDTO.getCategory());
            }

            if (updateTransactionDTO.getDescription() != null) {
                transaction.setDescription(updateTransactionDTO.getDescription());
            }

            repo.save(transaction);

            return "Transaction Updated Succefully";

        }else{

            return "Transaction is not Present";
        }

    }

    public String deleteTransaction() {

        return "Transaction deleted successfully";
    }
}

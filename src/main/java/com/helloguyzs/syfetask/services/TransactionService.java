package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.transaction.CreateTransactionRequest;
import com.helloguyzs.syfetask.dto.transaction.UpdateTransactionRequest;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.CategoryRepo;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    TransactionRepo repo;


    public List<Transaction> getAllTransaction(
            LocalDate startDate,
            LocalDate endDate,
            String categoryName,
            CategoryType type

    ) {

        int userId = 1;

        List<Transaction> transactions = repo.findByUserId(userId);

        return transactions.stream()
                .filter(txn -> startDate == null || !txn.getDate().isBefore(startDate))
                .filter(txn -> endDate == null || !txn.getDate().isAfter(endDate))
                .filter(txn -> categoryName == null || txn.getCategory().equalsIgnoreCase(categoryName))
                .filter(txn -> type == null || txn.getCategoryType() == type)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();
    }

    public String addTransaction(CreateTransactionRequest requestDTO) {

        // need to make the check of category

        int userId = 1;

        List<Category> categories = categoryRepo.findByUserId(userId);

        if (categories.stream().noneMatch(category -> category.getName().equals(requestDTO.getCategory()))) {
            return "Category does not exist";
        }

        Optional<Category>  categoryOpt = categoryRepo.findByUserIdAndName( userId, requestDTO.getCategory());
        if (categoryOpt.isEmpty()) {
            return "Category does not exist";
        }

        Category category = categoryOpt.get();
        Transaction transaction = new Transaction();


        transaction.setUserId(userId);
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDate(requestDTO.getDate());
        transaction.setCategory(requestDTO.getCategory());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setCategoryType(category.getType());

        repo.save(transaction);


        return "Transaction Added successfully";
    }


    public String updateTransaction(int updateTransactoinID, UpdateTransactionRequest updateTransactionDTO) {

        int userId = 1;

        Optional<Transaction> optTran = repo.findById(updateTransactoinID);

        if (optTran.isPresent()) {

            Transaction transaction = optTran.get();

            if (updateTransactionDTO.getAmount() != null) {

                if (updateTransactionDTO.getAmount() > 0) {
                    transaction.setAmount(updateTransactionDTO.getAmount());
                } else {
                    return "Amount should be greater than 0";
                }

            }

            if (updateTransactionDTO.getCategory() != null) {


                List<Category> categories =categoryRepo.findByUserId(userId);

                if (categories.stream()
                        .anyMatch(category -> category.getName()
                                .equals(updateTransactionDTO.getCategory()))) {

                    Optional<Category>  newCategory = categoryRepo.findByUserIdAndName( userId, updateTransactionDTO.getCategory());
                    Category category = newCategory.get();
                    transaction.setCategoryType(category.getType());
                    transaction.setCategory(updateTransactionDTO.getCategory());

                } else {
                    return "Category does not exist";
                }
            }


            if (updateTransactionDTO.getDescription() != null) {
                transaction.setDescription(updateTransactionDTO.getDescription());
            }

            repo.save(transaction);

            return "Transaction Updated Succefully";

        }

        return "Transaction not found";
    }

    public String deleteTransaction (Integer TransactionID){

        Optional<Transaction> transaction = repo.findById(TransactionID);

        if (transaction.isEmpty()) {
            return "Transaction not found";
        }

        repo.deleteById(TransactionID);
        return "Transaction deleted successfully";
    }

}

package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.transaction.*;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.exceptions.BadRequestException;
import com.helloguyzs.syfetask.exceptions.NotFoundException;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.CategoryRepo;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private CreateTransactionResponse getResponse(Transaction transaction) {
        CreateTransactionResponse response = new CreateTransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount().setScale(2, RoundingMode.HALF_UP));
        response.setDate(transaction.getDate());
        response.setCategory(transaction.getCategory());
        response.setDescription(transaction.getDescription());
        response.setType(transaction.getCategoryType());
        return response;
    }

    public GetTransactionReponse getAllTransaction(
            Integer userId,
            LocalDate startDate,
            LocalDate endDate,
            String categoryName,
            CategoryType type
    ) {
        List<Transaction> transactions =  repo.findByUserId(userId).stream()
                .filter(txn -> startDate == null || !txn.getDate().isBefore(startDate))
                .filter(txn -> endDate == null || !txn.getDate().isAfter(endDate))
                .filter(txn -> categoryName == null || txn.getCategory().equalsIgnoreCase(categoryName))
                .filter(txn -> type == null || txn.getCategoryType() == type)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();

        GetTransactionReponse response = new GetTransactionReponse();
        response.setTransactions(transactions.stream()
                .map(this::getResponse)
                .toList());

        return response ;

    }

    public CreateTransactionResponse addTransaction(Integer userId, CreateTransactionRequest requestDTO) {
        Optional<Category> categoryOpt = categoryRepo.findByUserIdAndName(userId, requestDTO.getCategory());
        if (categoryOpt.isEmpty()) {
            throw new BadRequestException("Category does not exist");
        }

        Category category = categoryOpt.get();

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(requestDTO.getAmount().setScale(2, RoundingMode.HALF_UP));
        transaction.setDate(requestDTO.getDate());
        transaction.setCategory(requestDTO.getCategory());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setCategoryType(category.getType());

        repo.save(transaction);

        CreateTransactionResponse response = getResponse(transaction);

        return response ;
    }

    public CreateTransactionResponse updateTransaction(Integer userId, int id, UpdateTransactionRequest requestDTO) {
        Transaction transaction = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new BadRequestException("Unauthorized access to transaction");
        }

        if (requestDTO.getAmount() != null) {
            transaction.setAmount(requestDTO.getAmount().setScale(2, RoundingMode.HALF_UP));
        }

        if (requestDTO.getCategory() != null) {
            Category category = categoryRepo.findByUserIdAndName(userId, requestDTO.getCategory())
                    .orElseThrow(() -> new BadRequestException("Category does not exist"));

            transaction.setCategoryType(category.getType());
            transaction.setCategory(requestDTO.getCategory());
        }

        if (requestDTO.getDescription() != null) {
            transaction.setDescription(requestDTO.getDescription());
        }

        repo.save(transaction);

        CreateTransactionResponse response = getResponse(transaction);
        return response;
    }

    public DeleteTransactionResponse deleteTransaction(Integer userId, Integer id) {
        Transaction transaction = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new BadRequestException("Unauthorized access to transaction");
        }

        repo.delete(transaction);

        DeleteTransactionResponse response = new DeleteTransactionResponse();
        response.setMessage("Transaction deleted successfully");
        return response;
    }
}

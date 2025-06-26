package com.helloguyzs.syfetask.repo;

import com.helloguyzs.syfetask.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends JpaRepository< Transaction , Integer> {

    List<Transaction> findByUserIdAndCategory( Integer userId, String categoryId);
    List<Transaction> findByUserId(Integer userId);
    List<Transaction> findByUserIdAndDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);





}

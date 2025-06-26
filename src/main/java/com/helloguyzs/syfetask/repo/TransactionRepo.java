package com.helloguyzs.syfetask.repo;

import com.helloguyzs.syfetask.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository< Transaction , Integer> {
}

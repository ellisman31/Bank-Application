package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJPA extends JpaRepository<Transaction, Long> {
}

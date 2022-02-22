package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferJPA extends JpaRepository<Transfer, Long> {
}

package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferJPA extends JpaRepository<Transfer, Long> {
}

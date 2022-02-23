package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJPA extends JpaRepository<Customer, Long> {

    Customer findByEmailAddress(String emailAddress);

}

package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPA extends JpaRepository<User, Long> {

    User findByEmailAddress(String emailAddress);

}

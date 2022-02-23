package com.bankapplication.bankapplication.JPARepository;

import com.bankapplication.bankapplication.Model.Role;
import com.bankapplication.bankapplication.Types.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJPA extends JpaRepository<Role, Long> {

    Role findByRoleName(RoleType roleName);

}

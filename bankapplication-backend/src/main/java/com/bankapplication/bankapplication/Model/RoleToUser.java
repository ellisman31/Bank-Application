package com.bankapplication.bankapplication.Model;

import com.bankapplication.bankapplication.Types.RoleType;
import lombok.Data;

@Data
public class RoleToUser {

    private Long customerId;
    private RoleType role;

}

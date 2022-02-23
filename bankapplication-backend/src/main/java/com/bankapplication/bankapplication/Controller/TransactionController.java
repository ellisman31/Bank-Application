package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Service.UserService;
import com.bankapplication.bankapplication.Service.TransactionService;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @RequestMapping(value="/api/doTransaction", method=RequestMethod.PUT)
    public void doTransactionForUser(@RequestBody Transaction transaction) {

        Long transferSenderId = 0L;

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User authorized = userService.ownInformation(email);
            transferSenderId = authorized.getId();
            if (transferSenderId > 0) {
                transaction.setUser(userService.getUserById(transferSenderId).get());
                transactionService.setTransactionForTheUser(transaction);
            }
        }
    }

}

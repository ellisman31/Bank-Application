package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Service.UserService;
import com.bankapplication.bankapplication.Service.TransactionService;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @RequestMapping(value="/api/doTransaction{transactionType}", method=RequestMethod.PUT)
    public ResponseEntity<Transaction> doTransactionForUser(@RequestBody Transaction transaction, @RequestParam("transactionType") String transactionType) {

        Long transferSenderId = 0L;

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User authorized = userService.ownInformation(email);
            transferSenderId = authorized.getId();
            if (transferSenderId > 0) {
                transaction.setUser(userService.getUserById(transferSenderId).get());
                String capitalizeTransactionType = transactionType.toUpperCase();
                transaction.setTransactionType(TransactionTypes.valueOf(capitalizeTransactionType));
                transactionService.setTransactionForTheUser(transaction);
                return ResponseEntity.status(HttpStatus.OK).body(transaction);
            }
        }
        else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return null;
    }

}

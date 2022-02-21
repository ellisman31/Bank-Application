package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Service.CustomerService;
import com.bankapplication.bankapplication.Service.TransactionService;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final CustomerService customerService;

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    @RequestMapping(value="/api/saveTransaction/inf{transactionType}{money}{customerId}", method=RequestMethod.PUT)
    public void saveTransactionForCustomer(
            @RequestParam("transactionType") String transactionType,
            @RequestParam("money") BigDecimal money,
            @RequestParam("customerId") Long customerId) {

        Transaction newTransaction = new Transaction(TransactionTypes.valueOf(transactionType), money);
        newTransaction.setCustomer(customerService.getCustomerById(customerId).get());
        transactionService.setTransactionToTheCustomer(newTransaction);

    }

}

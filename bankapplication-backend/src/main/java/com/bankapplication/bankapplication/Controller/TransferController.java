package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Model.Transfer;
import com.bankapplication.bankapplication.Service.CustomerService;
import com.bankapplication.bankapplication.Service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class TransferController {


    private final TransferService transferService;
    private final CustomerService customerService;

    @Autowired
    public TransferController(TransferService transferService, CustomerService customerService) {
        this.transferService = transferService;
        this.customerService = customerService;
    }

    @RequestMapping(value="/api/transferMoney/inf{receiverCustomerId}", method= RequestMethod.POST)
    public void transferMoney(@RequestBody Transfer transfer,
                              @RequestParam("receiverCustomerId") Long receiverCustomerId) {

        Long transferSenderId = 0L;

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer authorized = customerService.ownInformation(email);
            transferSenderId = authorized.getId();
            if (transferSenderId > 0 && !Objects.equals(receiverCustomerId, transferSenderId)) {
                transferService.transferMoneyToCustomer(transfer, receiverCustomerId, transferSenderId);
            }
        }
    }

}

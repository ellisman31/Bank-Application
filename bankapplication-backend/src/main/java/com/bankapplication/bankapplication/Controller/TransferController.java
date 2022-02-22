package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.Transfer;
import com.bankapplication.bankapplication.Service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TransferController {


    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @RequestMapping(value="/api/transferMoney/inf{receiverCustomerId}{transferSenderId}", method= RequestMethod.POST)
    public void transferMoney(@RequestBody Transfer transfer,
                              @RequestParam("receiverCustomerId") Long receiverCustomerId,
                              @RequestParam("transferSenderId") Long transferSenderId) {
        transferService.transferMoneyToCustomer(transfer, receiverCustomerId, transferSenderId);
    }


}

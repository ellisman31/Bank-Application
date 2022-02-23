package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Transfer;
import com.bankapplication.bankapplication.Service.UserService;
import com.bankapplication.bankapplication.Service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {


    private final TransferService transferService;
    private final UserService userService;

    @Autowired
    public TransferController(TransferService transferService, UserService userService) {
        this.transferService = transferService;
        this.userService = userService;
    }

    @RequestMapping(value="/api/transferMoney", method= RequestMethod.POST)
    public void transferMoney(@RequestBody Transfer transfer) {

        Long transferSenderId = 0L;

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User authorized = userService.ownInformation(email);
            transferSenderId = authorized.getId();
            if (transferSenderId > 0) {
                transferService.transferMoneyToUser(transfer, transfer.getTransferReceiverEmail(), transferSenderId);
            }
        }
    }

}

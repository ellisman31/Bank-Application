package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.UserJPA;
import com.bankapplication.bankapplication.JPARepository.TransactionJPA;
import com.bankapplication.bankapplication.JPARepository.TransferJPA;
import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Model.Transfer;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import com.bankapplication.bankapplication.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class TransferService {

    private final TransferJPA transferJPA;
    private final UserJPA userJPA;
    private final TransactionJPA transactionJPA;
    private final Util util;

    @Autowired
    public TransferService(TransferJPA transferJPA, UserJPA userJPA, TransactionJPA transactionJPA, Util util) {
        this.transferJPA = transferJPA;
        this.userJPA = userJPA;
        this.transactionJPA = transactionJPA;
        this.util = util;
    }

    public void transferMoneyToUser(Transfer transfer, Long receiverUserId, Long transferSenderId) {

        User transferReceiverUser = userJPA.getById(receiverUserId);
        User transferSenderUser = userJPA.getById(transferSenderId);

        BigDecimal transferSenderBalance = transferSenderUser.getBalance();
        BigDecimal transferReceiverBalance = transferReceiverUser.getBalance();
        BigDecimal transferMoney = transfer.getTransferMoney();

        if (transferSenderBalance.subtract(transferMoney).compareTo(BigDecimal.ZERO) >= 0) {
            transferReceiverUser.setBalance(transferMoney.add(transferReceiverBalance));
            transfer.setTransferUser(transferReceiverUser);

            String transferSenderFullName = transferSenderUser.getFirstName() + " " + transferSenderUser.getLastName();
            transfer.setTransferSenderName(transferSenderFullName);

            transferSenderUser.setBalance(transferSenderBalance.subtract(transferMoney));

            transferJPA.save(transfer);
            saveTransactionTransfer(transferSenderUser, transferMoney);
        }
    }

    public void saveTransactionTransfer(User transferSenderUser, BigDecimal transferMoney) {
        Transaction transactionTransfer = new Transaction(TransactionTypes.TRANSFER,transferMoney);
        transactionTransfer.setUser(transferSenderUser);
        transactionTransfer.setTransactionDate(util.currentDate());
        transactionJPA.save(transactionTransfer);
    }

}

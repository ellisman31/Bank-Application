package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.CustomerJPA;
import com.bankapplication.bankapplication.JPARepository.TransactionJPA;
import com.bankapplication.bankapplication.JPARepository.TransferJPA;
import com.bankapplication.bankapplication.Model.Customer;
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
    private final CustomerJPA customerJPA;
    private final TransactionJPA transactionJPA;
    private final Util util;

    @Autowired
    public TransferService(TransferJPA transferJPA, CustomerJPA customerJPA, TransactionJPA transactionJPA, Util util) {
        this.transferJPA = transferJPA;
        this.customerJPA = customerJPA;
        this.transactionJPA = transactionJPA;
        this.util = util;
    }

    public void transferMoneyToCustomer(Transfer transfer, Long receiverCustomerId, Long transferSenderId) {

        Customer transferReceiverCustomer = customerJPA.getById(receiverCustomerId);
        Customer transferSenderCustomer = customerJPA.getById(transferSenderId);

        BigDecimal transferSenderBalance = transferSenderCustomer.getBalance();
        BigDecimal transferReceiverBalance = transferReceiverCustomer.getBalance();
        BigDecimal transferMoney = transfer.getTransferMoney();

        if (transferSenderBalance.subtract(transferMoney).compareTo(BigDecimal.ZERO) >= 0) {
            transferReceiverCustomer.setBalance(transferMoney.add(transferReceiverBalance));
            transfer.setTransferCustomer(transferReceiverCustomer);

            String transferSenderFullName = transferSenderCustomer.getFirstName() + " " + transferSenderCustomer.getLastName();
            transfer.setTransferSenderName(transferSenderFullName);

            transferSenderCustomer.setBalance(transferSenderBalance.subtract(transferMoney));

            transferJPA.save(transfer);
            saveTransactionTransfer(transferSenderCustomer, transferMoney);
        }
    }

    public void saveTransactionTransfer(Customer transferSenderCustomer, BigDecimal transferMoney) {
        Transaction transactionTransfer = new Transaction(TransactionTypes.TRANSFER,transferMoney);
        transactionTransfer.setCustomer(transferSenderCustomer);
        transactionTransfer.setTransactionDate(util.currentDate());
        transactionJPA.save(transactionTransfer);
    }

}

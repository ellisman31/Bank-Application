package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.CustomerJPA;
import com.bankapplication.bankapplication.JPARepository.TransactionJPA;
import com.bankapplication.bankapplication.JPARepository.TransferJPA;
import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Model.Transfer;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final TransferJPA transferJPA;
    private final CustomerJPA customerJPA;
    private final TransactionJPA transactionJPA;

    @Autowired
    public TransferService(TransferJPA transferJPA, CustomerJPA customerJPA, TransactionJPA transactionJPA) {
        this.transferJPA = transferJPA;
        this.customerJPA = customerJPA;
        this.transactionJPA = transactionJPA;
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
            saveTransactionTransfer(transferSenderCustomer, transfer, transferMoney);
        }
    }

    public void saveTransactionTransfer(Customer transferSenderCustomer, Transfer transferToCustomer, BigDecimal transferMoney) {
        Transaction transactionTransferType = new Transaction(TransactionTypes.TRANSFER,transferMoney);
        transactionTransferType.setCustomer(transferSenderCustomer);
        transactionJPA.save(transactionTransferType);
    }

}

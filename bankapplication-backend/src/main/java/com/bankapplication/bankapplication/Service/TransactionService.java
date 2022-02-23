package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.TransactionJPA;
import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Model.Transaction;
import com.bankapplication.bankapplication.Types.TransactionTypes;
import com.bankapplication.bankapplication.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {

    private final TransactionJPA transactionJPA;
    private final Util util;

    @Autowired
    public TransactionService(TransactionJPA transactionJPA, Util util) {
        this.transactionJPA = transactionJPA;
        this.util = util;
    }

    public void saveTransaction(Transaction transaction, boolean successFulTransaction) {
        if (successFulTransaction) {
            transaction.setTransactionDate(util.currentDate());
            transactionJPA.save(transaction);
        }
    }

    public void deleteTransaction(Long transactionId) {
        transactionJPA.deleteById(transactionId);
    }

    public void setTransactionToTheCustomer(Transaction transaction) {

        Customer customer = transaction.getCustomer();
        BigDecimal customerMoney = customer.getBalance();
        BigDecimal transactionMoney = transaction.getMoney();
        TransactionTypes transactionType = transaction.getTransActionType();
        boolean successFulTransaction = false;

        if (transactionType.equals(TransactionTypes.DEPOSIT)) {
            customer.setBalance(customerMoney.add(transactionMoney));
            successFulTransaction = true;
        } else if (transactionType.equals(TransactionTypes.WITHDRAW)) {
            if (customerMoney.subtract(transactionMoney).compareTo(BigDecimal.ZERO) >= 0) {
                customer.setBalance(customerMoney.subtract(transactionMoney));
                successFulTransaction = true;
            }
        }

        saveTransaction(transaction, successFulTransaction);
    }


}

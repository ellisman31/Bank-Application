package com.bankapplication.bankapplication.Model;

import com.bankapplication.bankapplication.Types.TransactionTypes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="transaction", schema="public")
@JsonIgnoreProperties(value = {"id"})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private User user;

    @Column(name = "transaction_type")
    private TransactionTypes transactionType;
    @Column(name = "transactionMoney")
    private BigDecimal transactionMoney;
    @Column(name = "transactionDate")
    private Timestamp transactionDate;

    @ManyToMany
    @JoinColumn(name = "transfer_id")
    @JsonBackReference(value="user-movement")
    private List<Transfer> transferToUser;

    public Transaction(TransactionTypes transActionType, BigDecimal money) {
        this.transactionType = transActionType;
        this.transactionMoney = money;
    }

}

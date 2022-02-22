package com.bankapplication.bankapplication.Model;

import com.bankapplication.bankapplication.Types.TransactionTypes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private Customer customer;

    @Column(name = "transaction_type")
    private TransactionTypes transActionType;
    @Column(name = "money")
    private BigDecimal money;

    @ManyToOne
    @JoinColumn(name = "transfer_id")
    @JsonBackReference(value="user-movement")
    private Transfer transferToCustomer;

    public Transaction(TransactionTypes transActionType, BigDecimal money) {
        this.transActionType = transActionType;
        this.money = money;
    }

}

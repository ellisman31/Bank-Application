package com.bankapplication.bankapplication.Model;

import com.bankapplication.bankapplication.Types.TransactionTypes;
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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private TransactionTypes transActionType;
    private BigDecimal money;

}

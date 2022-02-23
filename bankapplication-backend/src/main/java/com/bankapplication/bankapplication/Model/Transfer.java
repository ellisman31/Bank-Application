package com.bankapplication.bankapplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="transfer", schema="public")
@JsonIgnoreProperties(value = {"id", "transactionHistory"})
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "transferSenderName")
    private String transferSenderName;
    @Column(name = "transferMoney")
    private BigDecimal transferMoney;
    @Column(name = "transferDate")
    private Timestamp transferDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer transferCustomer;

    @OneToMany(mappedBy = "transferToCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value="user-movement")
    private List<Transaction> transactionHistory;

}

package com.bankapplication.bankapplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "transferReceiver")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String transferReceiverEmail;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private User transferUser;

    @ManyToMany(mappedBy = "transferToUser", cascade = CascadeType.ALL)
    @JsonManagedReference(value="user-movement")
    private List<Transaction> transactionHistory;

}

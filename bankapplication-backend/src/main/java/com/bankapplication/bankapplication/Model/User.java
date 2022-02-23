package com.bankapplication.bankapplication.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customer", schema="public")
@JsonIgnoreProperties(value = {"id", "userPassword"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String emailAddress;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name="registration_date")
    private Timestamp registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "transferUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transfer> transfers;

    @ManyToMany(fetch = EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userPassword = password;
    }

    public User(Long id, String firstName, String lastName, String emailAddress, String password, BigDecimal balance, Timestamp registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userPassword = password;
        this.balance = balance;
        this.registrationDate = registrationDate;
    }
}

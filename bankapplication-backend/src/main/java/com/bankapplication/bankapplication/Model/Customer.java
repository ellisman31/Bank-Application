package com.bankapplication.bankapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customer", schema="public")
public class Customer {

    @Id
    private Long id;

    private String lastName;
    private String firstName;
    private String emailAddress;
    private String password;

    private double balance;


}

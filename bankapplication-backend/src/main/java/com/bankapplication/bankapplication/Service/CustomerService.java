package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.CustomerJPA;
import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerJPA customerJPA;
    private final CustomerUtil customerUtil = new CustomerUtil();

    @Autowired
    public CustomerService(CustomerJPA customerJPA) {
        this.customerJPA = customerJPA;
    }

    public List<Customer> getAllCustomer() {
        return customerJPA.findAll();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerJPA.findById(customerId);
    }

    public void saveCustomer(Customer customer) {
        BigDecimal defaultBalance = customerUtil.defaultBalance();
        Timestamp registrationDate = customerUtil.registrationDate();
        customer.setRegistrationDate(registrationDate);
        customer.setBalance(defaultBalance);
        customerJPA.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerJPA.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerJPA.deleteById(customerId);
    }

    public BigDecimal customerBalance(Long customerId) {
        return customerJPA.findById(customerId).get().getBalance();
    }



}

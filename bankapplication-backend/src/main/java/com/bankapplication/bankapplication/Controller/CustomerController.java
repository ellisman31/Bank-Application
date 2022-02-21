package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value="/api/getAllCustomer", method=RequestMethod.GET)
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @RequestMapping(value="/api/getCustomer/{customerId}", method=RequestMethod.GET)
    public Optional<Customer> getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping(value="/api/deleteCustomer/{customerId}", method=RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @RequestMapping(value="/api/updateCustomer", method=RequestMethod.PUT)
    public void updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
    }

    @RequestMapping(value="/api/saveCustomer", method=RequestMethod.POST)
    public void saveCustomer(Customer customer) {
        customerService.saveCustomer(customer);
    }

    @RequestMapping(value="/api/customerBalance/{customerId}", method=RequestMethod.GET)
    public BigDecimal customerBalance(@PathVariable Long customerId) {
        return customerService.customerBalance(customerId);
    }

}

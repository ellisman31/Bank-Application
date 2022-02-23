package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.CustomerJPA;
import com.bankapplication.bankapplication.JPARepository.RoleJPA;
import com.bankapplication.bankapplication.Model.Customer;
import com.bankapplication.bankapplication.Model.Role;
import com.bankapplication.bankapplication.Types.RoleType;
import com.bankapplication.bankapplication.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class CustomerService implements UserDetailsService {

    private final CustomerJPA customerJPA;
    private final Util util = new Util();
    private final RoleJPA roleJPA;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerJPA customerJPA, RoleJPA roleJPA, PasswordEncoder passwordEncoder) {
        this.customerJPA = customerJPA;
        this.roleJPA = roleJPA;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerJPA.findByEmailAddress(email);

        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found in the database");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        customer.getRoles().forEach(role -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
            authorities.add(grantedAuthority);
        });

        return new org.springframework.security.core.userdetails.User(customer.getEmailAddress(),
                customer.getPassword(), authorities);
    }

    public List<Customer> getAllCustomer() {
        return customerJPA.findAll();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerJPA.findById(customerId);
    }

    public void saveCustomer(Customer customer) {
        BigDecimal defaultBalance = util.defaultBalance();
        Timestamp registrationDate = util.currentDate();
        customer.setRegistrationDate(registrationDate);
        customer.setBalance(defaultBalance);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerJPA.save(customer);
        addRuleToUser(customer.getId(), RoleType.USER);
    }

    public void addRuleToUser(Long customerId, RoleType roleName) {
        Customer customer = customerJPA.findById(customerId).get();
        Role role = roleJPA.findByRoleName(roleName);
        customer.getRoles().add(role);
    }

    public Customer ownInformation(String email) {
        return customerJPA.findByEmailAddress(email);
    }

    public BigDecimal ownBalance(String email) {
        return customerJPA.findByEmailAddress(email).getBalance();
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

    public void saveRole(Role role) {
        roleJPA.save(role);
    }

}

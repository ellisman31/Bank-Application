package com.bankapplication.bankapplication.Service;

import com.bankapplication.bankapplication.JPARepository.UserJPA;
import com.bankapplication.bankapplication.JPARepository.RoleJPA;
import com.bankapplication.bankapplication.Model.User;
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
public class UserService implements UserDetailsService {

    private final UserJPA userJPA;
    private final Util util = new Util();
    private final RoleJPA roleJPA;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserJPA userJPA, RoleJPA roleJPA, PasswordEncoder passwordEncoder) {
        this.userJPA = userJPA;
        this.roleJPA = roleJPA;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJPA.findByEmailAddress(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
            authorities.add(grantedAuthority);
        });

        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),
                user.getPassword(), authorities);
    }

    public List<User> getAllUser() {
        return userJPA.findAll();
    }

    public Optional<User> getUserById(Long userIdId) {
        return userJPA.findById(userIdId);
    }

    public void saveUser(User user) {

        if(checkRegisteredUser(user.getEmailAddress())) {
            BigDecimal defaultBalance = util.defaultBalance();
            Timestamp registrationDate = util.currentDate();
            user.setRegistrationDate(registrationDate);
            user.setBalance(defaultBalance);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userJPA.save(user);
            addRuleToUser(user.getId(), RoleType.USER);
        }

    }

    public boolean checkRegisteredUser(String email) {
        User user = userJPA.findByEmailAddress(email);
        return user == null;
    }

    public void addRuleToUser(Long userIdId, RoleType roleName) {
        User user = userJPA.findById(userIdId).get();
        Role role = roleJPA.findByRoleName(roleName);
        user.getRoles().add(role);
    }

    public User ownInformation(String email) {
        return userJPA.findByEmailAddress(email);
    }

    public BigDecimal ownBalance(String email) {
        return userJPA.findByEmailAddress(email).getBalance();
    }

    public void updateUser(User user) {
        userJPA.save(user);
    }

    public void deleteUser(Long userId) {
        userJPA.deleteById(userId);
    }

    public BigDecimal userBalance(Long userId) {
        return userJPA.findById(userId).get().getBalance();
    }

    public void saveRole(Role role) {
        roleJPA.save(role);
    }

    public User findUserByEmail(String emailAddress) {
        return userJPA.findByEmailAddress(emailAddress);
    }
}

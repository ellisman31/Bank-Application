package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Role;
import com.bankapplication.bankapplication.Model.RoleToUser;
import com.bankapplication.bankapplication.Service.UserService;
import com.bankapplication.bankapplication.Types.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/api/getAllUser", method=RequestMethod.GET)
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @RequestMapping(value="/api/getUser/{userId}", method=RequestMethod.GET)
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long userId) {
        if (userService.getUserById(userId).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/getUser/{bankAccountNumber}", method=RequestMethod.GET)
    public ResponseEntity<User> getUserByBankAccountNumber(@PathVariable String bankAccountNumber) {
        User user = userService.findUserBankAccountNumber(bankAccountNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/getUser/{emailAddress)}", method=RequestMethod.GET)
    public ResponseEntity<User> getUserByEmailAddress(@PathVariable String emailAddress) {
        User user = userService.findUserByEmail(emailAddress);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/ownInformation", method=RequestMethod.GET)
    public ResponseEntity<User> getOwnInformation() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.status(HttpStatus.OK).body(userService.ownInformation(email));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/ownBalance", method=RequestMethod.GET)
    public ResponseEntity<BigDecimal> getOwnBalance() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.status(HttpStatus.OK).body(userService.ownBalance(email));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/deleteUser/{userId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String returnMessage = "";
        if (userService.getUserById(userId).isPresent()) {
            Optional<User> user = userService.getUserById(userId);
            userService.deleteUser(userId);
            returnMessage = user.get().getId() + " user id has been deleted from the database!";
            return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
        }
        else {
            returnMessage = "User can not be deleted from the database because it's not present!";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnMessage);
        }
    }

    @RequestMapping(value="/api/updateUser", method=RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        String returnMessage = user.getId() + " user id successfully updated their information!";
        return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
    }

    @RequestMapping(value="/auth/api/registration", method=RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String returnMessage = "";
        if (userService.findUserByEmail(user.getEmailAddress()) == null &&
                user.getFirstName() != null && user.getLastName() != null && user.getEmailAddress() != null && user.getPassword() != null) {
            userService.saveUser(user);
            returnMessage = "User successfully created!";
            return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
        }
        else {
            returnMessage = "User can not be created because something went wrong! Please check everything and try again!";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnMessage);
        }
    }

    @RequestMapping(value="/api/userBalance/{userId}", method=RequestMethod.GET)
    public ResponseEntity<BigDecimal> userBalance(@PathVariable Long userId) {
        if (userService.getUserById(userId).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.userBalance(userId));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value="/api/user/role", method=RequestMethod.POST)
    public ResponseEntity<String> saveUserRole(@RequestBody Role role) {
        userService.saveRole(role);
        String returnMessage = role.getRoleName() + " role has been successfully saved!";
        return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
    }

    @RequestMapping(value="/api/addNewRoleToUser", method=RequestMethod.POST)
    public ResponseEntity<String> addRoleToUser(@RequestBody RoleToUser roleToUser) {
        String returnMessage = "";
        Long customerId = roleToUser.getCustomerId();
        RoleType role = roleToUser.getRole();

        if (userService.getUserById(customerId).isPresent()) {
            userService.addRuleToUser(roleToUser.getCustomerId(), role);
            returnMessage = "The " + role + " role has been successfully added to " + customerId + " user id!";
            return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
        }
        else {
            returnMessage = "The " +  role + " has been not added to the user!";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnMessage);
        }

    }

}

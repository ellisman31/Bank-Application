package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Model.Role;
import com.bankapplication.bankapplication.Model.RoleToUser;
import com.bankapplication.bankapplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<User> getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value="/api/ownInformation", method=RequestMethod.GET)
    public User getOwnInformation() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.ownInformation(email);
        } else {
            return null;
        }

    }

    @RequestMapping(value="/api/ownBalance", method=RequestMethod.GET)
    public BigDecimal getOwnBalance() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.ownBalance(email);
        } else {
            return null;
        }
    }

    @RequestMapping(value="/api/deleteUser/{userId}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @RequestMapping(value="/api/updateUser", method=RequestMethod.PUT)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @RequestMapping(value="/auth/api/registration", method=RequestMethod.POST)
    public void registerUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(value="/api/userBalance/{userId}", method=RequestMethod.GET)
    public BigDecimal userBalance(@PathVariable Long userId) {
        return userService.userBalance(userId);
    }

    @RequestMapping(value="/api/user/role", method=RequestMethod.POST)
    public void saveUserRole(@RequestBody Role role) {
        userService.saveRole(role);
    }

    @RequestMapping(value="/api/addNewRoleToUser", method=RequestMethod.POST)
    public void addRoleToUser(@RequestBody RoleToUser roleToUser) {
        userService.addRuleToUser(roleToUser.getCustomerId(), roleToUser.getRole());
    }

}

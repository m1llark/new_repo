package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.MyValidator;

import java.util.List;

@RestController
public class MainRestController {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MainRestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/users")
    public List<User> showAllUsers() {

        return userService.listUsers();
    }

    @GetMapping("/roles")
    public List<Role> showRoles() {
        return roleService.listRoles();
    }

    @GetMapping("/users/authenticated")
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);

    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.loadUserById(id);
    }


    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {

        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.loadUserById(id).getPassword());
            userService.updateUser(user);
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.updateUser(user);
        }
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.removeUserById(id);
    }

}

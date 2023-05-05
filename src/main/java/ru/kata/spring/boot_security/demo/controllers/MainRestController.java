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
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.MyValidator;

import java.util.List;

@RestController
public class MainRestController {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MyValidator myValidator;

    @Autowired
    public MainRestController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MyValidator myValidator) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.myValidator = myValidator;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {

        return userService.listUsers();
    }

    @GetMapping("/roles")
    public List<Role> showRoles() {
        return roleRepository.findAll();
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
            userService.saveUser(user);
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        }
        return user;
    }




    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.removeUserById(id);
    }

}

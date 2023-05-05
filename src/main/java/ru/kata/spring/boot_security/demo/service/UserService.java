package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.*;


public interface UserService extends UserDetailsService {

    public List<User> listUsers();

    public boolean removeUserById(Long userId);

    public User saveUser(User user);


    public User updateUser(User user);

    public User loadUserById(Long id);

}

package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        return user;
    }





    public User loadUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public List<Role> listRoles() {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("ADMIN"));
        }
        return roleRepository.findAll();
    }
    @Transactional
    public boolean removeUserById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}


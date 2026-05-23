package com.lets_play.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lets_play.model.Role;
import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // hash password using BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default role User
        // if (user.getRole() == null) {
        user.setRole(Role.USER);
        // }

        return userRepository.save(user);
    }

}

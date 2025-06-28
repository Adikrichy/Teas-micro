package com.teashop.user.service;

import com.teashop.user.dto.JwtResponse;
import com.teashop.user.dto.LoginRequest;
import com.teashop.user.dto.SignupRequest;
import com.teashop.user.model.User;

import java.util.List;

public interface UserService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    User registerUser(SignupRequest signupRequest);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 
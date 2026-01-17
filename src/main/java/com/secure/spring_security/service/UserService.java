package com.secure.spring_security.service;

import com.secure.spring_security.dtos.UserDTO;
import com.secure.spring_security.model.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);
}

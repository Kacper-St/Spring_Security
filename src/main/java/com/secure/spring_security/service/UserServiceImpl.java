package com.secure.spring_security.service;

import com.secure.spring_security.dtos.UserDTO;
import com.secure.spring_security.model.AppRole;
import com.secure.spring_security.model.Role;
import com.secure.spring_security.model.User;
import com.secure.spring_security.repositories.RoleRepository;
import com.secure.spring_security.repositories.UserRepository;
import com.secure.spring_security.security.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(( ) -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }


    public UserInfoResponse getUserDetailsResponse(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRole() != null ?
                List.of(user.getRole().getRoleName().name()) :
                List.of();

        return new UserInfoResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.isTwoFactorEnabled(),
                roles
        );
    }

    public String getCurrentUsername(UserDetails userDetails) {
        if (userDetails == null) {
            return "";
        }
        return userDetails.getUsername();
    }
}

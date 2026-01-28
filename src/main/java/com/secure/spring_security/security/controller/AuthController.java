package com.secure.spring_security.security.controller;

import com.secure.spring_security.security.request.LoginRequest;
import com.secure.spring_security.security.request.SignupRequest;
import com.secure.spring_security.security.response.LoginResponse;
import com.secure.spring_security.security.response.MessageResponse;
import com.secure.spring_security.security.response.UserInfoResponse;
import com.secure.spring_security.security.service.AuthService;
import com.secure.spring_security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/public/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/public/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        UserInfoResponse response = userService.getUserDetailsResponse(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username")
    public ResponseEntity<String> currentUserName(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userService.getCurrentUsername(userDetails);
        return ResponseEntity.ok(username);
    }
}
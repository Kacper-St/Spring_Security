package com.secure.spring_security.security.service;

import com.secure.spring_security.security.request.LoginRequest;
import com.secure.spring_security.security.request.SignupRequest;
import com.secure.spring_security.security.response.LoginResponse;
import com.secure.spring_security.security.response.UserInfoResponse;
import jakarta.validation.Valid;

public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
    void registerUser(@Valid SignupRequest signUpRequest);
    UserInfoResponse getUserDetailsResponse(String username);
}

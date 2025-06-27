package com.helloguyzs.syfetask.controller;

import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return authService.login(loginRequest , request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("You have been logged out successfully.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> whoami(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }
}

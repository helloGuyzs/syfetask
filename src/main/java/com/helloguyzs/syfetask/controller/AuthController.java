package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.LoginResponse;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterResponse;
import com.helloguyzs.syfetask.models.Users;
import com.helloguyzs.syfetask.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request, HttpServletRequest req) {
        return authService.login(request, req);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        return authService.logout(req);
    }

    @GetMapping("/me")
    public Users getCurrentUser(HttpServletRequest req) {
        return authService.getCurrentUser(req);
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @GetMapping("/private")
    public String privateEndpoint(HttpServletRequest req) {
        Users user = authService.getCurrentUser(req);
        return "Welcome " + user.getId() + ", this is a private endpoint.";
    }
}

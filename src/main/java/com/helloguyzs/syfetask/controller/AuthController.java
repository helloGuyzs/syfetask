package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.LoginResponse;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterResponse;
import com.helloguyzs.syfetask.models.Users;
import com.helloguyzs.syfetask.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public RegisterResponse Register(@RequestBody @Valid RegisterRequest request){
        return authService.register(request);
    }


//    @GetMapping("/users")
//    public List<Users> getUser(){
//        return authService.getUser();
//    }


    @RequestMapping("/login")
    public LoginResponse Login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }


    @RequestMapping("/logout")
    public String Logout() {
        return "Thank you for coming to helloguyzs";
    }
}

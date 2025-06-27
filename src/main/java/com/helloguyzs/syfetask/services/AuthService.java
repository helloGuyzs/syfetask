package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.models.Users;
import com.helloguyzs.syfetask.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest registerDto) {
        Optional<Users> existing = repo.findByUsername(registerDto.getUsername());
        if (existing.isPresent()) {
            return "User already exists with username: " + registerDto.getUsername();
        }

        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Encrypt password
        user.setFullName(registerDto.getFullName());
        user.setPhone(registerDto.getPhoneNumber());

        Users savedUser = repo.save(user);

        categoryService.createDefaultCatogaries(savedUser.getId()); // Use saved user ID
        return "User registered successfully";
    }

    public String login(LoginRequest loginDto, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getSession(true);
            return "Login successful";
        } catch (Exception e) {
            return "Invalid username or password";
        }
    }

    public List<Users> getUser() {
        return repo.findAll();
    }
}

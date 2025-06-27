package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.LoginResponse;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterResponse;
import com.helloguyzs.syfetask.exceptions.BadRequestException;
import com.helloguyzs.syfetask.exceptions.ConflictException;
import com.helloguyzs.syfetask.exceptions.UnauthorizedException;
import com.helloguyzs.syfetask.models.Users;
import com.helloguyzs.syfetask.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepo repo;

    public RegisterResponse register(RegisterRequest registerDto) {
        // Check if user already exists
        if (repo.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ConflictException("User already exists with username: " + registerDto.getUsername());
        }

        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setFullName(registerDto.getFullName());
        user.setPhone(registerDto.getPhoneNumber());

        Users savedUser = repo.save(user);


        categoryService.createDefaultCatogaries(savedUser.getId());

        RegisterResponse response = new RegisterResponse();

        response.setMessage("User registered successfully");
        response.setUserId(savedUser.getId());

        return response;
    }

    public LoginResponse login(LoginRequest loginDto) {
        Optional<Users> userOpt = repo.findByUsername(loginDto.getUsername());

        if (userOpt.isEmpty()) {
            throw new UnauthorizedException("User does not exist");
        }

        Users user = userOpt.get();
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");

        return response;
    }

    public List<Users> getUser() {
        return repo.findAll();
    }
}

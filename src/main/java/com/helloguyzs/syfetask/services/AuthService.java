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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepo repo;

    public RegisterResponse register(RegisterRequest registerDto) {
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

        return new RegisterResponse("User registered successfully", savedUser.getId());
    }



    public LoginResponse login(LoginRequest loginDto, HttpServletRequest request) {
        Users user = repo.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("User does not exist"));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }


        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, List.of());


        SecurityContextHolder.getContext().setAuthentication(authToken);


        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


        session.setAttribute("user", user);

        return new LoginResponse("Login successful");
    }


    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "Logout successful. Session invalidated.";
    }

    public Users getCurrentUser(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");

        if (user == null || !(user instanceof Users)) {
            throw new UnauthorizedException("User is not logged in");
        }

        return (Users) user;
    }

    public List<Users> getUser() {
        return repo.findAll();
    }
}

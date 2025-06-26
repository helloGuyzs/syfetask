package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.auth.LoginRequest;
import com.helloguyzs.syfetask.dto.auth.RegisterRequest;
import com.helloguyzs.syfetask.models.Users;
import com.helloguyzs.syfetask.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepo repo;





    public String register(RegisterRequest registerDto) {


        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setFullName(registerDto.getFullName());
        user.setPhone(registerDto.getPhoneNumber());

        repo.save(user);
        return "USer registered successfully";
    }


    public String login(LoginRequest loginDto ){

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

       Optional< Users > userOpt = repo.findByUsername(username);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();

            if (user.getPassword().equals(password)){

                return "Login Successfully";
            }else {

                return "Invalid Password";
            }

        } else {

            return " User does not exist";

        }

    }

    public List<Users> getUser() {
        return repo.findAll();
    }
}

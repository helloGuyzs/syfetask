package com.helloguyzs.syfetask.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 3 , message = "Name should contain minimum 3 letters")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+\\d{10}$",
            message = "Phone number must start with '+' and contain exactly 10 digits"
    )
    private String phoneNumber;

}

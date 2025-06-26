package com.helloguyzs.syfetask.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Enter the Username")
    private String username ;

    @NotBlank(message = "Enter the Password")
    private String password ;

}

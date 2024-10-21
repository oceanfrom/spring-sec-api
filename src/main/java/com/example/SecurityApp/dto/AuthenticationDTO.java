package com.example.SecurityApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationDTO {

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters long")
    private String username;
    @NotEmpty(message = "Password must not be empty")
    @Column(name = "password")
    private String password;
}

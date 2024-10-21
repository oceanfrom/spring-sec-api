package com.example.SecurityApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "Person")
@Data
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters long")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
}

package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    private Integer UserId;

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String pic;
    private String role; // 可以是 "USER", "ADMIN" 等
}
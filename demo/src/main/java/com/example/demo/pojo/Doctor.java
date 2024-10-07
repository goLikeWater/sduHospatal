package com.example.demo.pojo;

import lombok.Data;

@Data
public class Doctor {
    private Integer id;
    private User user;
    private Person person;
    private Department department;
    private String introduce;
}

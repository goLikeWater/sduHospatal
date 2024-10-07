package com.example.demo.pojo;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String num;
    private Person person;
    private User user;
    private Double money;
}

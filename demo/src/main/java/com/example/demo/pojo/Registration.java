package com.example.demo.pojo;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Registration {
    private Integer id;
    private String registrationNum;
    private Appointment appointment;
    private LocalTime time;
    private int state;
}

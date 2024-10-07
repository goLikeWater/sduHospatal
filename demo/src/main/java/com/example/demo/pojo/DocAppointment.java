package com.example.demo.pojo;

import lombok.Data;

@Data
public class DocAppointment {
    private Integer id;
    private Doctor doctor;
    private String date;
    private int num;
    private float fee;
}

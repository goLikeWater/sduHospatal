package com.example.demo.pojo;

import lombok.Data;

@Data
public class Appointment {
    private Integer id;
    private String appointmentNum;
    private DocAppointment docAppointment;
    private Patient patient;
    private int state;
}

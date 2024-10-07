package com.example.demo.service;

import com.example.demo.pojo.Appointment;

import java.util.List;

public interface AppointmentService {

    void add(Appointment appointment);

    Appointment findById(Integer id);

    void delete(Integer id);

    void update(Appointment appointment);

    List<Appointment> findByPatientId(Integer patientId);

    List<Appointment> findAll();

    List<Appointment> finByUsername(String username);

    Appointment findByAppointmentNum(String appointmentNum);

    List<Appointment> finByPatientNum(String patientNum);
}

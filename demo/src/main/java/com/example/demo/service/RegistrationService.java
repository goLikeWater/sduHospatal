package com.example.demo.service;

import com.example.demo.pojo.Registration;

import java.util.List;

public interface RegistrationService {
    void add(Registration registration);

    Registration findById(Integer id);

    void update(Registration registration);

    void delete(Integer id);

    Registration findByRegistrationNum(String num);

    List<Registration> findByPatientId(Integer patientId);

    List<Registration> findAll();

    List<Registration> findByDoctorId(Integer doctorId);
}

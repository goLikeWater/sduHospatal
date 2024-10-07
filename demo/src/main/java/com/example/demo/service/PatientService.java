package com.example.demo.service;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findByPatientnum(String num);

    void add(Patient patient);

    Patient findById(Integer id);

    void delete(Integer id);

    void changeMoney(Patient patient);

    Patient findByUserId(Integer userId);

    Patient findByNum(String num);
}

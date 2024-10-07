package com.example.demo.service;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.User;

import java.util.List;

public interface DoctorService {
    List<Doctor> findByDoctorname(String doctorname);
    Doctor findById(Integer id);
    Doctor findByUserId(Integer id);
    void add(Doctor d);

    void delete(Integer id);
}

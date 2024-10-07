package com.example.demo.service;

import com.example.demo.pojo.DocAppointment;

import java.util.List;

public interface DocAppointmentService {


    DocAppointment findById(Integer id);

    void add(DocAppointment docAppointment);

    void delete(Integer id);

    void update(DocAppointment docAppointment);

    List<DocAppointment> findByDoctorId(Integer doctorId);

    List<DocAppointment> findAll();

    List<DocAppointment> findByUser(String date, Integer departmentId);
}

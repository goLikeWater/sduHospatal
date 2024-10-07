package com.example.demo.service.ipl;

import com.example.demo.mapper.AppointmentMapper;
import com.example.demo.pojo.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentIpl implements AppointmentService {
    @Autowired
    AppointmentMapper appointmentMapper;

    @Override
    public Appointment findByAppointmentNum(String num) {
        return appointmentMapper.findByAppointmentNum(num);
    }

    @Override
    public List<Appointment> finByPatientNum(String patientNum) {
        return appointmentMapper.findByPatientNumUsername(patientNum);

    }

    @Override
    public void add(Appointment appointment) {
        appointmentMapper.add(appointment);
    }

    @Override
    public Appointment findById(Integer id) {
        return appointmentMapper.findById(id);
    }

    @Override
    public void delete(Integer id) {
        appointmentMapper.delete(id);
    }

    @Override
    public void update(Appointment appointment) {
        appointmentMapper.update(appointment);
    }

    @Override
    public List<Appointment> findByPatientId(Integer patientId) {
        return appointmentMapper.findByPatient(patientId);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentMapper.findAll();
    }

    @Override
    public List<Appointment> finByUsername(String username) {
        return appointmentMapper.findByUsername(username);
    }
}

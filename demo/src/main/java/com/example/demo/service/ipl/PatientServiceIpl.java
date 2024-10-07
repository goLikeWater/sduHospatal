package com.example.demo.service.ipl;

import com.example.demo.mapper.PatientMapper;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Patient;
import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceIpl implements PatientService {
    @Autowired
    PatientMapper patientMapper;
    @Override
    public List<Patient> findByPatientnum(String num) {
        return patientMapper.findByPatientnum(num);
    }

    @Override
    public void add(Patient patient) {
        patientMapper.add(patient);
    }

    @Override
    public Patient findById(Integer id) {
        return patientMapper.findById(id);
    }

    @Override
    public void delete(Integer id) {
        patientMapper.delete(id);
    }

    @Override
    public void changeMoney(Patient patient) {
        patientMapper.changeMoney(patient);
    }

    @Override
    public Patient findByUserId(Integer userId) {
        return patientMapper.findByUserId(userId);
    }

    @Override
    public Patient findByNum(String num) {
        return patientMapper.findByNum(num);


    }


}

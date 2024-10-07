package com.example.demo.service.ipl;

import com.example.demo.mapper.RegistrationMapper;
import com.example.demo.pojo.Registration;
import com.example.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegistrationServiceIpl implements RegistrationService {

    @Autowired
    RegistrationMapper registrationMapper;


    @Override
    public void add(Registration registration) {
        registrationMapper.add(registration);
    }

    @Override
    public Registration findById(Integer id) {
       return registrationMapper.findById(id);
    }

    @Override
    public void update(Registration registration) {
        registrationMapper.update(registration);
    }

    @Override
    public void delete(Integer id) {
        registrationMapper.delete(id);
    }

    @Override
    public Registration findByRegistrationNum(String num) {
        return registrationMapper.findByRegistrationNum(num);
    }

    @Override
    public List<Registration> findByPatientId(Integer patientId) {
        return registrationMapper.findByPatientId(patientId);
    }

    @Override
    public List<Registration> findAll() {
        return registrationMapper.findAll();
    }

    @Override
    public List<Registration> findByDoctorId(Integer doctorId) {
        return registrationMapper.findByDoctorId(doctorId);
    }
}

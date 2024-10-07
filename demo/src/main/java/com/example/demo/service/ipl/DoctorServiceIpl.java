package com.example.demo.service.ipl;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.User;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceIpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public List<Doctor> findByDoctorname(String doctorname) {
        List<Doctor> l = doctorMapper.findByDoctorname(doctorname);
        return l;
    }

    @Override
    public Doctor findById(Integer id) {
        Doctor d = doctorMapper.findById(id);
        return d;
    }

    @Override
    public Doctor findByUserId(Integer id) {
        return doctorMapper.findByUserId(id);
    }

    @Override
    public void add(Doctor d) {
        doctorMapper.add(d);
    }

    @Override
    public void delete(Integer id) {
        doctorMapper.delete(id);
    }

}

package com.example.demo.service.ipl;

import com.example.demo.mapper.DocAppointmentMapper;
import com.example.demo.pojo.DocAppointment;
import com.example.demo.service.DocAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocAppointmentServiceIpl implements DocAppointmentService {

    @Autowired
    DocAppointmentMapper docAppointmentMapper;

    @Override
    public DocAppointment findById(Integer id) {
        return docAppointmentMapper.findById(id);
    }
    @Override
    public void add(DocAppointment docAppointment) {
        docAppointmentMapper.add(docAppointment);
    }
    @Override
    public void delete(Integer id) {
        docAppointmentMapper.delete(id);
    }
    @Override
    public void update(DocAppointment docAppointment) {
        docAppointmentMapper.update(docAppointment);
    }

    @Override
    public List<DocAppointment> findByDoctorId(Integer doctorId) {
        return docAppointmentMapper.findByDoctorId(doctorId);
    }

    @Override
    public List<DocAppointment> findAll() {
        return docAppointmentMapper.findAll();

    }

    @Override
    public List<DocAppointment> findByUser(String date, Integer departmentId) {
        return docAppointmentMapper.findByUser(date,departmentId);
    }
}

package com.example.demo.service.ipl;

import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.pojo.Department;
import com.example.demo.pojo.Result;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceIpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public Department findById(Integer id) {
        return departmentMapper.findById(id);
    }

    @Override
    public void add(Department d) {
        departmentMapper.add(d);
    }

    @Override
    public void delete(Integer id) {
        departmentMapper.delete(id);
    }

    @Override
    public void update(Department d) {
        departmentMapper.update(d);
    }

    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.getDepartmentList();
    }
}

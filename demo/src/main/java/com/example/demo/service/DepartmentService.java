package com.example.demo.service;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;

import java.util.List;

public interface DepartmentService {
    Department findById(Integer id);
    void add(Department d);
    void delete(Integer id);
    void update(Department d);

    List<Department> getDepartmentList();
}

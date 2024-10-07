package com.example.demo.service;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Person;

public interface PersonService {
    Person findById(Integer id);
    void add(Person d);
    void delete(Integer id);
    void update(Person d);
}

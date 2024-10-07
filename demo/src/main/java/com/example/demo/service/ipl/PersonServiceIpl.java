package com.example.demo.service.ipl;

import com.example.demo.mapper.PersonMapper;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceIpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;
    @Override
    public Person findById(Integer id) {
        return personMapper.findById(id);
    }

    @Override
    public void add(Person p) {
        personMapper.add(p);
    }

    @Override
    public void delete(Integer id) {
        personMapper.delete(id);
    }

    @Override
    public void update(Person d) {
        personMapper.update(d);
    }
}

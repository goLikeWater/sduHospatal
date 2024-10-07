package com.example.demo.controllers;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.Result;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {
    @Autowired
    PersonService personService;
    @PostMapping("/add")
    public Result add(@RequestBody Map m){
        Person p = new Person();
        String name = (String) m.get("name");
        String gender = (String) m.get("gender");
        String birthday = (String) m.get("birthday");
        String phone = (String) m.get("phone");
        String address = (String) m.get("address");

        p.setName(name);
        p.setAddress(address);
        p.setGender(gender);
        p.setPhone(phone);
        p.setBirthday(birthday);
        personService.add(p);
        return Result.success();
    }
    @PostMapping("/delete")
    public Result delete(Integer id){
        Person p = personService.findById(id);

        if(p!=null){
            personService.delete(id);
            return Result.success();
        }
        return Result.error("请选择删除的人员");
    }
    @PostMapping("/update")
    public Result update(@RequestBody Map m){
        Integer id = (Integer) m.get("id");
        String name = (String) m.get("name");
        String gender = (String) m.get("gender");
        String birthday = (String) m.get("birthday");
        String phone = (String) m.get("phone");
        String address = (String) m.get("address");


        Person p = new Person();
        p.setPersonId(id);
        p.setName(name);
        p.setAddress(address);
        p.setBirthday(birthday);
        p.setPhone(phone);
        p.setGender(gender);
        personService.update(p);
        return Result.success();
    }
}

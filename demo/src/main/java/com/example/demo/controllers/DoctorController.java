package com.example.demo.controllers;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PersonService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private DepartmentService departmentService;
    public Map getMapFromDoctor(Doctor d) {
        Map m = new HashMap();
        if(d == null)
            return m;
        m.put("doctorId", d.getId());
        m.put("department",d.getDepartment().getDname());
        m.put("introduce",d.getIntroduce());
        Person p = d.getPerson();
        m.put("username",d.getUser().getUsername());
        m.put("personId", p.getPersonId());
        m.put("name",p.getName());
        String gender = p.getGender();
        m.put("gender",gender);
        m.put("birthday", p.getBirthday());
        m.put("email",d.getUser().getEmail());
        m.put("phone",p.getPhone());
        m.put("address",p.getAddress());
        return m;
    }
    public List getDoctorMapList(String name) {
        List dataList = new ArrayList();
        List<Doctor> dList = doctorService.findByDoctorname(name);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDoctor(dList.get(i)));
        }
        return dataList;
    }
    @PostMapping("/getDoctorList")
    public Result<List> getDoctorList( @Param(value = "name") String name) {
        System.out.println(name);
        List dataList = getDoctorMapList(name);
        System.out.println("=====================================");
        System.out.println(dataList);
        return Result.success(dataList);  //按照测试框架规范会送Map的list
    }

    @PostMapping("/add")
    public Result add(@RequestBody Map m){
        if(userService.findByUsername((String) m.get("username")) !=null)
            return Result.error("用户名已存在");
        if(departmentService.findById((Integer)m.get("departmentId")) ==null)
            return Result.error("请选择正确的科室");

        User u = new User();
        String username = (String) m.get("username");
        String password = (String)m.get("password");
        u.setUsername(username);
        u.setPassword(password);

        Person p =new Person();
        p.setName((String)m.get("name"));
        p.setGender((String)m.get("gender"));
        p.setAddress((String)m.get("address"));
        p.setBirthday((String)m.get("birthday"));
        p.setPhone((String)m.get("phone"));

        personService.add(p);
        userService.register(u);

        Doctor d = new Doctor();
        d.setUser(u);
        d.setPerson(p);
        d.setDepartment(departmentService.findById((Integer)m.get("departmentId")));
        d.setIntroduce((String)m.get("introduce"));
        doctorService.add(d);


        return Result.success();

    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        System.out.println(id);
        Doctor d = doctorService.findById(id);
        System.out.println(d);
        userService.delete(d.getUser().getUserId());
        personService.delete(d.getPerson().getPersonId());
        doctorService.delete(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map m){

        Integer id = (Integer) m.get("doctorId");
        String birthday = (String) m.get("birthday");
        String phone = (String) m.get("phone");
        String address = (String) m.get("address");
        String email = (String) m.get("email");

        Doctor d = doctorService.findById(id);
        Person p = d.getPerson();
        User u = d.getUser();

        p.setBirthday(birthday);
        p.setPhone(phone);
        p.setAddress(address);
        personService.update(p);
        u.setEmail(email);
        userService.update(u);

        return Result.success();
    }


}

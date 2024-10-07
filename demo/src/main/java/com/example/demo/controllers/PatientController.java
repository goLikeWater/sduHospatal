package com.example.demo.controllers;

import com.example.demo.pojo.*;
import com.example.demo.service.*;
import com.example.demo.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    public Map getMapFromDoctor(Patient p) {
        Map m = new HashMap();
        if(p == null)
            return m;
        m.put("patientId", p.getId());
        m.put("num",p.getNum());
        Person person = p.getPerson();
        m.put("username",p.getUser().getUsername());
        m.put("personId", person.getPersonId());
        m.put("name",person.getName());
        String gender = person.getGender();
        m.put("gender",gender);
        m.put("birthday", person.getBirthday());
        m.put("email",p.getUser().getEmail());
        m.put("phone",person.getPhone());
        m.put("address",person.getAddress());
        return m;
    }
    public List getPatientMapList(String num) {
        List dataList = new ArrayList();
        List<Patient> dList = patientService.findByPatientnum(num);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDoctor(dList.get(i)));
        }
        return dataList;
    }
    @PostMapping("/list")
    public Result<List> getPatientList(@Param(value = "num") String num) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        if(!Objects.equals(username, "admin"))
            return Result.error("您无权限查看!");


        List dataList = getPatientMapList(num);
        return Result.success(dataList);  //按照测试框架规范会送Map的list
    }

    @PostMapping("/add")
    public Result add(@RequestBody Map m){
        if(m.get("num") == null || ((String)m.get("num")).length() == 0)
            return Result.error("请输入证件号");
        if(patientService.findByNum((String) m.get("num")) !=null)
            return Result.error("当前证件号已存在");
        if(userService.findByUsername((String) m.get("username")) !=null)
            return Result.error("用户名已存在");
        User u = new User();
        String username = (String) m.get("username");
        String password = (String)m.get("password");
        if(username.length()<5||username.length()>10)
            return Result.error("用户名不符合规范");
        if(password.length()<5||password.length()>10)
            return Result.error("密码不符合规范");
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

        Patient patient = new Patient();
        patient.setUser(u);
        patient.setPerson(p);
        patient.setNum((String)m.get("num"));
        patientService.add(patient);


        return Result.success();

    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        Patient patient = patientService.findById(id);
        patientService.delete(id);
        userService.delete(patient.getUser().getUserId());
        personService.delete(patient.getPerson().getPersonId());
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map m){
        Integer id = (Integer) m.get("patientId");
        String birthday = (String) m.get("birthday");
        String phone = (String) m.get("phone");
        String address = (String) m.get("address");
        String email = (String) m.get("email");

        Patient patient = patientService.findById(id);
        Person p = patient.getPerson();
        User u = patient.getUser();

        p.setBirthday(birthday);
        p.setPhone(phone);
        p.setAddress(address);
        personService.update(p);
        u.setEmail(email);
        userService.update(u);

        return Result.success();
    }

    @PostMapping("changeMoney")
    public Result changeMoney(@RequestBody Map m){
        Integer id = (Integer) m.get("patientId");
        double mny = Double.parseDouble((String) m.get("mny"));
        Patient patient = patientService.findById(id);

        if(mny<0){
            if(patient.getMoney()+mny<0)
                return Result.error("余额不足");
        }
        patient.setMoney(patient.getMoney()+mny);
        patientService.changeMoney(patient);
        return Result.success();

    }
}

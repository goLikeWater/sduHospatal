package com.example.demo.controllers;

import com.example.demo.pojo.*;
import com.example.demo.service.*;
import com.example.demo.utils.RandomStringGenerator;
import com.example.demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    PatientService patientService;
    @Autowired
    DocAppointmentService docAppointmentService;
    @Autowired
    DoctorService doctorService;



    @RequestMapping("/add")
    public Result add(@RequestBody Map m){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer patientId;
        Patient patient;
        Appointment appointment;
        if(patientService.findByUserId((Integer)map.get("userId")) ==null) {
            patientId = (Integer) m.get("patientId");

            if (patientService.findById(patientId) == null)
                return Result.error("请传入正确的患者信息");
            List<Appointment> dlist = appointmentService.findByPatientId(patientId);
            int cond = -1;
            for (int i = 0; i < dlist.size(); i++) {
                if (dlist.get(i).getState() == 0) cond = i;
            }
            if (cond == -1) return Result.error("您当前无预约信息");
            appointment = dlist.get(cond);

            patient = patientService.findById(patientId);

        }
        else {
            patient = patientService.findByUserId((Integer)map.get("userId"));
            List<Appointment> dlist = appointmentService.findByPatientId(patient.getId());
            int cond = -1;
            for (int i = 0; i < dlist.size(); i++) {
                if (dlist.get(i).getState() == 0) cond = i;
            }
            if (cond == -1) return Result.error("您当前无预约信息");
            appointment = dlist.get(cond);
        }
        if (patient.getMoney() < appointment.getDocAppointment().getFee())
            return Result.error("余额不足，无法挂号");
        String num = RandomStringGenerator.generateRandomString();
        while(registrationService.findByRegistrationNum(num) != null){
            num = RandomStringGenerator.generateRandomString();
        }


        patient.setMoney(patient.getMoney()-appointment.getDocAppointment().getFee());

        patientService.changeMoney(patient);

        appointment.setState(1);

        appointmentService.update(appointment);

        Registration registration = new Registration();
        registration.setAppointment(appointment);
        registration.setRegistrationNum(num);
        registration.setTime(LocalTime.now());

        registrationService.add(registration);

        return Result.success();
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        if(registrationService.findById(id) == null)
            return Result.error("请确认挂号信息是否错误");
        registrationService.delete(id);
        return Result.success();


    }

    @RequestMapping("/update")
    public Result update(@RequestBody Map m){
        Integer id = (Integer) m.get("id");
        Object numObj = m.get("state");
        int state = (numObj instanceof Integer) ? (Integer) numObj : Integer.parseInt(numObj.toString());


        Registration registration = registrationService.findById(id);

        registration.setState(state);

        registrationService.update(registration);

        return Result.success();


    }



    public Map getMapFromRegistration(Registration r) {
        Map m = new HashMap();
        if(r == null)
            return m;
        m.put("id", r.getId());
        m.put("time",r.getTime());
        m.put("registrationNum",r.getRegistrationNum());
        m.put("state",r.getState());

        Appointment a = r.getAppointment();
        a = appointmentService.findById(a.getId());
        m.put("appointmentNum",a.getAppointmentNum());
        Patient patient = a.getPatient();
        patient = patientService.findById(patient.getId());
        m.put("patientId",patient.getId());
        m.put("patientNum",patient.getNum());
        m.put("patientName",patient.getPerson().getName());

        DocAppointment docAppointment = a.getDocAppointment();
        docAppointment = docAppointmentService.findById(docAppointment.getId());
        Doctor doctor = docAppointment.getDoctor();
        doctor = doctorService.findById(doctor.getId());
        m.put("doctorName",doctor.getPerson().getName());
        m.put("department",doctor.getDepartment().getDname());
        m.put("date",docAppointment.getDate());

        return m;
    }
    public List getRegistrationMapListP(Integer patientId) {
        List dataList = new ArrayList();
        List<Registration> rList = registrationService.findByPatientId(patientId);  //数据库查询操作
        if(rList == null || rList.size() == 0)
            return dataList;
        for(int i = 0; i < rList.size();i++) {
            dataList.add(getMapFromRegistration(rList.get(i)));
        }
        return dataList;
    }
    public List getRegistrationMapList() {
        List dataList = new ArrayList();
        List<Registration> rList = registrationService.findAll();  //数据库查询操作
        if(rList == null || rList.size() == 0)
            return dataList;
        for(int i = 0; i < rList.size();i++) {
            dataList.add(getMapFromRegistration(rList.get(i)));
        }
        return dataList;
    }
    public List getRegistrationMapListD(Integer doctorId) {
        List dataList = new ArrayList();
        List<Registration> rList = registrationService.findByDoctorId(doctorId);  //数据库查询操作
        if(rList == null || rList.size() == 0)
            return dataList;
        for(int i = 0; i < rList.size();i++) {
            dataList.add(getMapFromRegistration(rList.get(i)));
        }
        return dataList;
    }
    @GetMapping("/list")
    public Result<List> getRegistrationList() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        List<Appointment> dataList;
        if(patientService.findByUserId(userId) != null){
            dataList = getRegistrationMapListP(patientService.findByUserId(userId).getId());
        }
        else if(userId == 40){
            dataList = getRegistrationMapList();
        }
        else {
            Doctor doctor = doctorService.findByUserId(userId);
            dataList = getRegistrationMapListD(doctor.getId());
        }
        return Result.success(dataList);  //按照测试框架规范会送Map的list
    }



}

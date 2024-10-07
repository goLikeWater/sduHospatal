package com.example.demo.controllers;

import com.example.demo.pojo.*;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.DocAppointmentService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.utils.RandomStringGenerator;
import com.example.demo.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    DocAppointmentService docAppointmentService;


    public Map getMapFromAppointment(Appointment a) {
        Map m = new HashMap();
        if(a == null)
            return m;
        m.put("id", a.getId());
        m.put("appointmentNum",a.getAppointmentNum());
        m.put("state",a.getState());

        Patient patient = a.getPatient();
        patient = patientService.findById(patient.getId());
        m.put("patientId",patient.getId());
        m.put("patientNum",patient.getNum());
        m.put("patientName",patient.getPerson().getName());

        DocAppointment docAppointment = a.getDocAppointment();
        docAppointment = docAppointmentService.findById(docAppointment.getId());
        Doctor doctor = docAppointment.getDoctor();
        doctor = doctorService.findById(doctor.getId());
        m.put("doctorNum",doctor.getUser().getUsername());
        m.put("doctorName",doctor.getPerson().getName());
        m.put("department",doctor.getDepartment().getDname());
        m.put("date",docAppointment.getDate());
        m.put("fee",docAppointment.getFee());

        return m;
    }
    public List getAppointmentMapList(Integer patientId) {
        List dataList = new ArrayList();
        List<Appointment> dList = appointmentService.findByPatientId(patientId);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromAppointment(dList.get(i)));
        }
        return dataList;
    }
    public List getAppointmentMapList() {
        List dataList = new ArrayList();
        List<Appointment> dList = appointmentService.findAll();  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromAppointment(dList.get(i)));
        }
        return dataList;
    }
    public List getAppointmentMapList(String username) {
        List dataList = new ArrayList();
        List<Appointment> dList = appointmentService.finByUsername(username);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromAppointment(dList.get(i)));
        }
        return dataList;
    }

    public List getAppointmentMapList(String patientNum,int n){
        List dataList = new ArrayList();
        List<Appointment> dList = appointmentService.finByPatientNum(patientNum);
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromAppointment(dList.get(i)));
        }
        return dataList;

    }


    @PostMapping("/list")
    public Result<List> getAppointmentList(@RequestBody Map m) {
        System.out.println("111111111111111111111111111111111111111111111111111111");
        System.out.println(m);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        List<Appointment> dataList;
        if(patientService.findByUserId(userId) != null){
             dataList = getAppointmentMapList(patientService.findByUserId(userId).getId());
        }
        else if(userId == 40){
            if(m.get("patientNum")!=null)
                dataList = getAppointmentMapList((String) m.get("patientNum"),1);
             else dataList = getAppointmentMapList();
        }
        else {
            Doctor doctor = doctorService.findByUserId(userId);
            dataList = getAppointmentMapList(doctor.getUser().getUsername());
        }


        return Result.success(dataList);  //按照测试框架规范会送Map的list
    }


    @PostMapping("/add")
    public Result add(@RequestBody Map m){
        Map<String,Object> map =ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");

        Integer patientId;

        if(patientService.findByUserId(userId) == null){

            patientId = (Integer) m.get("patientId");
            if(patientService.findById(patientId) ==null){
                return Result.error("信息错误");
            }
        }
        else{
            patientId = patientService.findByUserId(userId).getId();
        }

        Integer docAppointmentId= (Integer) m.get("docAppointmentId");
        if( docAppointmentService.findById(docAppointmentId) == null)
            return Result.error("信息错误");

        if(appointmentService.findByPatientId(patientId) != null){
            List<Appointment> dlist = appointmentService.findByPatientId(patientId);
            for(int i = 0;i<dlist.size();i++){
                if(dlist.get(i).getState() == 0)return Result.error("您当前已预约");
            }
        }

        Patient patient = patientService.findById(patientId);
        DocAppointment docAppointment = docAppointmentService.findById(docAppointmentId);
        if(docAppointment.getNum() == 0){
            return Result.error("所选医生当天预约人数已满，请更换医生");
        }
        docAppointment.setNum(docAppointment.getNum()-1);
        docAppointmentService.update(docAppointment);

        Appointment appointment = new Appointment();
        String num = RandomStringGenerator.generateRandomString();
        while(appointmentService.findByAppointmentNum(num) != null){
            num = RandomStringGenerator.generateRandomString();
        }

        appointment.setAppointmentNum(num);
        appointment.setPatient(patient);
        appointment.setDocAppointment(docAppointment);

        appointmentService.add(appointment);

        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        if(appointmentService.findById(id) == null)
            return Result.error("请选择正确的预约信息进行删除");
        DocAppointment docAppointment = appointmentService.findById(id).getDocAppointment();
        docAppointment.setNum(docAppointment.getNum()+1);
        docAppointmentService.update(docAppointment);

        appointmentService.delete(id);
        return Result.success();

    }

    @PostMapping("update")
    public Result update(@RequestBody Map m){
        Integer id = (Integer) m.get("id");

        Appointment appointment = appointmentService.findById(id);

        if(m.get("docAppointmentId")!=null){
            Integer docAppointmentId = (Integer) m.get("docAppointmentId");
            DocAppointment nDocAppointment = docAppointmentService.findById(docAppointmentId);
            DocAppointment oDocAppointment = appointment.getDocAppointment();
            if(nDocAppointment.getNum()-1<0)
                return Result.error("所选医生当天预约人数已满");
            oDocAppointment.setNum(oDocAppointment.getNum()+1);
            nDocAppointment.setNum(nDocAppointment.getNum()-1);

            docAppointmentService.update(oDocAppointment);
            docAppointmentService.update(nDocAppointment);
            appointment.setDocAppointment(nDocAppointment);
        }

        if(m.get("state")!=null){
            appointment.setState((Integer) m.get("state"));
        }

        appointmentService.update(appointment);

        return Result.success();

    }



}

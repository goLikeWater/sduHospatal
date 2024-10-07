package com.example.demo.controllers;

import com.example.demo.pojo.*;
import com.example.demo.service.DocAppointmentService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


//医生预约信息
@RestController
@RequestMapping("/docAppointment")
public class DocAppointmentController {
    @Autowired
    UserService userService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    PatientService patientService;
    @Autowired
    DocAppointmentService docAppointmentService;

    @PostMapping("/add")
    public Result add(@RequestBody Map m){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");

        System.out.println("=-=-=-==-=-=-==-=-=-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(m);
        Doctor doctor = doctorService.findByUserId(userId);

        if(doctor == null)
            if(!Objects.equals((String) map.get("username"), "admin"))
                return Result.error("无权限");
        if(m.get("doctorId")!=null && doctor == null)
            doctor = doctorService.findById((Integer) m.get("doctorId"));
        String date = (String) m.get("date");
        Object numObj = m.get("num");
        int num = (numObj instanceof Integer) ? (Integer) numObj : Integer.parseInt(numObj.toString());

        Object feeObj = m.get("fee");
        Float fee = (feeObj instanceof Float) ? (Float) feeObj : Float.parseFloat(feeObj.toString());


        DocAppointment docAppointment = new DocAppointment();
        docAppointment.setDoctor(doctor);
        docAppointment.setDate(date);
        docAppointment.setNum(num);
        docAppointment.setFee(fee);

        docAppointmentService.add(docAppointment);

        return Result.success();



    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        if(docAppointmentService.findById(id) !=null) {
            docAppointmentService.delete(id);
            return Result.success();
        }
        else return Result.error("未选择预约信息");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map m){
        Integer docAppointmentId = (Integer) m.get("docAppointmentId");
        String date = (String) m.get("date");

        Object numObj = m.get("num");
        int num = (numObj instanceof Integer) ? (Integer) numObj : Integer.parseInt(numObj.toString());

        Object feeObj = m.get("fee");
        Float fee = (feeObj instanceof Float) ? (Float) feeObj : Float.parseFloat(feeObj.toString());


        DocAppointment docAppointment = docAppointmentService.findById(docAppointmentId);
        docAppointment.setNum(num);
        docAppointment.setDate(date);
        docAppointment.setFee(fee);

        docAppointmentService.update(docAppointment);
        return Result.success();
    }

    public Map getMapFromDocAppointment(DocAppointment docAppointment) {
        Map m = new HashMap();
        if(docAppointment == null)
            return m;
        m.put("docAppointmentId", docAppointment.getId());
        Doctor doctor = docAppointment.getDoctor();
        doctor = doctorService.findById(doctor.getId());
        m.put("username", doctor.getUser().getUsername());
        m.put("doctorId", doctor.getId());
        m.put("doctorName",doctor.getPerson().getName());

        m.put("date",docAppointment.getDate());
        m.put("num",docAppointment.getNum());
        m.put("fee",docAppointment.getFee());
        return m;
    }
    public List dGetDocAppointmentMapList(Integer doctorId) {
        List dataList = new ArrayList();
        List<DocAppointment> dList = docAppointmentService.findByDoctorId(doctorId);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDocAppointment(dList.get(i)));
        }
        return dataList;
    }
    public List uGetDocAppointmentMapList(String date,Integer departmentId) {
        List dataList = new ArrayList();
        List<DocAppointment> dList = docAppointmentService.findByUser(date,departmentId);  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDocAppointment(dList.get(i)));
        }
        return dataList;
    }
    public List getAllDocAppointmentMapList() {
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        if(!Objects.equals(username, "admin"))return new ArrayList<>();

        List dataList = new ArrayList();
        List<DocAppointment> dList = docAppointmentService.findAll();  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDocAppointment(dList.get(i)));
        }
        return dataList;
    }



    @PostMapping("/list")
    public Result<List> getAllDocAppointmentList(@RequestBody Map m) {
        System.out.println("========================================date");
        System.out.println(m);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");

        List dataList;

        if (patientService.findByUserId(userId) != null) {
            String date = (String) m.get("date");
            Integer departmentId = (Integer) m.get("departmentId");
//            Integer departmentId = 4;
            dataList = uGetDocAppointmentMapList(date, departmentId);
        } else if (userId == 40) {
            if (m != null) {
                String date = (String) m.get("date");
                Integer departmentId = (Integer) m.get("departmentId");
//                Integer departmentId = 4;
                if (date != null && departmentId != null)
                    dataList = uGetDocAppointmentMapList(date, departmentId);
                else
                    dataList = getAllDocAppointmentMapList();
            } else
                dataList = getAllDocAppointmentMapList();
        }
        else {
            Doctor doctor = doctorService.findByUserId(userId);
            Integer doctorId = doctorService.findByUserId(userId).getId();
            dataList = dGetDocAppointmentMapList(doctorId);
        }


        return Result.success(dataList);  //按照测试框架规范会送Map的list


    }


}

package com.example.demo.controllers;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.Result;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public Result add( String dname,String address){
        Department d = new Department();
        d.setDname(dname);
        d.setAddress(address);
        departmentService.add(d);
        return Result.success();
    }
    @PostMapping("/delete")
    public Result delete(Integer id){
        Department d = departmentService.findById(id);
        if(d!=null){
            departmentService.delete(id);
            return Result.success();
        }
        return Result.error("请选择删除的科室");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map m){
        Integer departmentId = (Integer) m.get("departmentId");
        String dname = (String) m.get("dname");
        String address = (String) m.get("address");

        Department d = new Department();
        d.setDepartmentId(departmentId);
        d.setDname(dname);
        d.setAddress(address);

        departmentService.update(d);
        return Result.success();
    }
    public Map getMapFromDepartment(Department d) {
        Map m = new HashMap();
        if(d == null)
            return m;
        m.put("departmentId", d.getDepartmentId());
        m.put("dname",d.getDname());
        m.put("address",d.getAddress());
        return m;
    }
    public List getDepartmentMapList() {
        List dataList = new ArrayList();
        List<Department> dList = departmentService.getDepartmentList();  //数据库查询操作
        if(dList == null || dList.size() == 0)
            return dataList;
        for(int i = 0; i < dList.size();i++) {
            dataList.add(getMapFromDepartment(dList.get(i)));
        }
        return dataList;
    }
    @GetMapping("/getDepartmentList")
    public Result<List> getDepartmentList(){
        return Result.success(getDepartmentMapList());
    }
}

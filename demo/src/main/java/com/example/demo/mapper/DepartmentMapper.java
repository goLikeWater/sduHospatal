package com.example.demo.mapper;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Result;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department where departmentId = #{id}")
    Department findById(Integer id);

    @Insert("insert into department (dname,address) values(#{dname},#{address})")
    void add(Department d);
    @Delete("delete from department where departmentId = #{id}")
    void delete(Integer id);
    @Update("update department set dname = #{dname},address = #{address} where department_id = #{department_id}")
    void update(Department d);

    @Select("select * from department ")
    List<Department> getDepartmentList();
}

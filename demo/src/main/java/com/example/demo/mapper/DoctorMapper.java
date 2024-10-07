package com.example.demo.mapper;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface DoctorMapper {
    @Select("SELECT d.* FROM doctor d  JOIN person p ON d.personId = p.personId WHERE p.name LIKE CONCAT('%', #{doctorname}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),
            @Result(property = "department", column = "departmentId", one = @One(select = "selectDepartmentById"))
    })
    List<Doctor> findByDoctorname(String doctorname);

    @Select("select * from doctor where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),
            @Result(property = "department", column = "departmentId", one = @One(select = "selectDepartmentById"))

    })
    Doctor findById(Integer id);

    @Insert("insert into Doctor (userId, personId, departmentId, introduce) values(#{user.userId}, #{person.personId}, #{department.departmentId}, #{introduce})")
    void add(Doctor d);

    @Delete("delete from doctor where id = #{id}")
    void delete(Integer id);
    @Select("select * from doctor where userId = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),
            @Result(property = "department", column = "departmentId", one = @One(select = "selectDepartmentById"))

    })
    Doctor findByUserId(Integer id);






    @Select("SELECT * FROM person WHERE personId = #{id}")
    Person selectPersonById(Integer id);
    @Select("SELECT * FROM user WHERE userId = #{id}")
    User selectUserById(Integer id);
    @Select("SELECT * FROM department WHERE departmentId = #{id}")
    Department selectDepartmentById(Integer id);

}

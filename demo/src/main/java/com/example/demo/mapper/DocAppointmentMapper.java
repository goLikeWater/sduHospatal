package com.example.demo.mapper;

import com.example.demo.pojo.DocAppointment;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DocAppointmentMapper {

    @Select("select * from docAppointment where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "doctor", column = "doctorId", one = @One(select = "selectDoctorById"))
    })
    DocAppointment findById(Integer id);

    @Select("select * from doctor where id = #{id}")
    Doctor selectDoctorById(Integer id);


    @Insert("insert into docAppointment (doctorId, date, num,fee) values(#{doctor.id}, #{date}, #{num}, #{fee})")
    void add(DocAppointment docAppointment);

    @Delete("delete from docAppointment where id = #{id}")
    void delete(Integer id);

    @Update("update docAppointment set num = #{num},date=#{date},fee=#{fee} where id = #{id}")
    void update(DocAppointment docAppointment);
    @Select("select * from docAppointment where doctorId = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "doctor", column = "doctorId", one = @One(select = "selectDoctorById"))
    })
    List<DocAppointment> findByDoctorId(Integer doctorId);


    @Select("select * from docAppointment ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "doctor", column = "doctorId", one = @One(select = "selectDoctorById"))
    })
    List<DocAppointment> findAll();
    @Select("SELECT da.*\n" +
            "FROM docAppointment da\n" +
            "JOIN doctor d ON da.doctorId = d.id\n" +
            "WHERE da.date = #{date}\n" +
            "AND d.departmentId = #{departmentId}; \n")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "doctor", column = "doctorId", one = @One(select = "selectDoctorById"))
    })
    List<DocAppointment> findByUser(String date,Integer departmentId);
}

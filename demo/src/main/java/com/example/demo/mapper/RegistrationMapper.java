package com.example.demo.mapper;

import com.example.demo.pojo.Appointment;
import com.example.demo.pojo.Registration;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegistrationMapper {

    @Insert("insert into registration ( registrationNum,appointmentId,state,time) values(#{registrationNum}, #{appointment.id}, 0,#{time})")
    void add(Registration registration);



    @Update("update registration set state = #{state} where id = #{id}")
    void update(Registration registration);

    @Delete("delete from registration where id = #{id}")
    void delete(Integer id);



    @Select("SELECT * FROM registration WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointment", column = "appointmentId", one = @One(select = "selectAppointmentById")),
    })
    Registration findById(Integer id);

    @Select("SELECT * FROM registration WHERE registrationNum = #{num}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointment", column = "appointmentId", one = @One(select = "selectAppointmentById")),
    })
    Registration findByRegistrationNum(String num);
    @Select("SELECT r.*\n" +
            "FROM registration r\n" +
            "JOIN appointment a ON r.appointmentId = a.id\n" +
            "JOIN patient p ON a.patientId = p.id\n" +
            "WHERE p.id = #{patientId};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointment", column = "appointmentId", one = @One(select = "selectAppointmentById")),
    })
    List<Registration> findByPatientId(Integer patientId);

    @Select("SELECT r.*\n" +
            "FROM registration r\n" +
            "JOIN appointment a ON r.appointmentId = a.id\n" +
            "JOIN docAppointment da ON a.docAppointmentId = da.id\n"+
            "WHERE da.doctorId = #{doctorId};\n")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointment", column = "appointmentId", one = @One(select = "selectAppointmentById")),
    })
    List<Registration> findByDoctorId(Integer doctorId);

    @Select("SELECT * FROM registration ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appointment", column = "appointmentId", one = @One(select = "selectAppointmentById")),
    })
    List<Registration> findAll();

    @Select("SELECT * FROM appointment where id = #{id}")
    Appointment selectAppointmentById(Integer id);
}

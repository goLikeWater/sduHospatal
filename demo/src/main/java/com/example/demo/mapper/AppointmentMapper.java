package com.example.demo.mapper;

import com.example.demo.pojo.Appointment;
import com.example.demo.pojo.DocAppointment;
import com.example.demo.pojo.Patient;
import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    @Select("SELECT * FROM appointment WHERE appointmentNum = #{appointmentNum}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    Appointment findByAppointmentNum(String appointmentNum);


    @Select("SELECT * FROM appointment WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    Appointment findById(Integer id);

    @Select("SELECT * FROM appointment where patientId = #{patientId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    List<Appointment> findByPatient(Integer patientId);

    @Select("SELECT * FROM appointment ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    List<Appointment> findAll();
    @Select("SELECT a.*\n" +
            "FROM appointment a\n" +
            "JOIN docAppointment da ON a.docAppointmentId = da.id\n" +
            "JOIN doctor d ON da.doctorId = d.id\n" +
            "JOIN user u ON d.userId = u.userId\n" +
            "WHERE u.username = #{username}  ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    List<Appointment> findByUsername(String username);


    @Delete("delete from appointment where id = #{id}")
    void delete(Integer id);

    @Update("update appointment set appointmentNum=#{appointmentNum}, patientId=#{patient.id}, docAppointmentId=#{docAppointment.id},state =#{state}  where id = #{id}")
    void update(Appointment appointment);

    @Insert("insert into appointment (appointmentNum, patientId, docAppointmentId,state) values(#{appointmentNum}, #{patient.id}, #{docAppointment.id},0)")
    void add(Appointment appointment);


    @Select("SELECT * FROM docAppointment WHERE id = #{id}")
    DocAppointment selectDocAppointmentById(Integer id);
    @Select("SELECT * FROM patient WHERE id = #{id}")
    Patient selectPatientById(Integer id);
    @Select("SELECT a.*\n" +
            "FROM appointment a\n" +
            "JOIN docAppointment da ON a.docAppointmentId = da.id\n" +
            "JOIN patient p ON a.patientId = p.id\n" +
            "JOIN user u ON p.userId = u.userId\n" +
            "WHERE p.num LIKE CONCAT('%', #{patientNum}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "docAppointment", column = "docAppointmentId", one = @One(select = "selectDocAppointmentById")),
            @Result(property = "patient", column = "patientId", one = @One(select = "selectPatientById"))
    })
    List<Appointment> findByPatientNumUsername(String patientNum);
}

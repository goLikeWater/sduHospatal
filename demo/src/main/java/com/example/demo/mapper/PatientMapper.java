package com.example.demo.mapper;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Patient;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PatientMapper {

    @Select("SELECT p.* FROM patient p   WHERE p.num LIKE CONCAT('%', #{num}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),

    })
    List<Patient> findByPatientnum(String num);

    @Select("SELECT p.* FROM patient p   WHERE p.num = #{num}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),

    })
    Patient findByNum(String num);


    @Insert("insert into Patient (userId, personId, num,money) values(#{user.userId}, #{person.personId}, #{num}, 0)")
    void add(Patient patient);

    @Select("SELECT * FROM patient WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),

    })
    Patient findById(Integer id);

    @Delete("delete from patient where id = #{id}")
    void delete(Integer id);
    @Update("update patient set money = #{money} where id = #{id}")
    void changeMoney(Patient patient);

    @Select("select * from patient where userId = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "personId", one = @One(select = "selectPersonById")),
            @Result(property = "user", column = "userId", one = @One(select = "selectUserById")),

    })
    Patient findByUserId(Integer id);



    @Select("SELECT * FROM person WHERE personId = #{id}")
    Person selectPersonById(Integer id);
    @Select("SELECT * FROM user WHERE userId = #{id}")
    User selectUserById(Integer id);

}

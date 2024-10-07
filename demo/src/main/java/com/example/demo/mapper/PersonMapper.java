package com.example.demo.mapper;

import com.example.demo.pojo.Department;
import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PersonMapper {
    @Select("select * from person where id = #{id}")
    Person findById(Integer id);

    @Insert("insert into person (name,gender,birthday,phone,address) values(#{name},#{gender},#{birthday},#{phone},#{address})")
    @Options(useGeneratedKeys = true, keyProperty = "personId")
    void add(Person p);
    @Delete("delete from person where personId = #{id}")
    void delete(Integer id);
    @Update("update person set birthday = #{birthday},phone = #{phone},address = #{address} where personId = #{personId}")
    void update(Person p);
}

package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("SELECT * from sys_user")
//    List<User> findAll();

//    @Insert("INSERT into sys_user(username, password, nickname, email, phone, address) VALUES (#{username}, "
//        + "#{password}, #{nickname}, #{email}, #{phone}, #{address})")
//    int insert(User user);\

//    Have to comment out to make mybatis plus work

//    int update(User user);

//    int deleteById(@Param("id") Integer id);

//    @Select("select * from sys_user where username like concat('%', #{username}, '%')"
//        + "and email LIKE CONCAT('%', #{email}, '%') "
//        + "and address LIKE CONCAT('%', #{address}, '%') "
//        + "limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String email, String address);
//
//    @Select("select count(*) from sys_user where username like concat('%', #{username}, '%') "
//        + "and email LIKE CONCAT('%', #{email}, '%') "
//        + "and address LIKE CONCAT('%', #{address}, '%')")
//    Integer selectTotal(String username, String email, String address);
}

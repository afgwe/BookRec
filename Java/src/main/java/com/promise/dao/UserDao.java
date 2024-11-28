package com.promise.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.promise.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT id,username,password,identity,status FROM tb_user WHERE username = #{username}")
    List<User> getByUsername(String username);

    @Select("SELECT * FROM tb_user WHERE id = #{user_id}")
    User getById(Integer user_id);

    @Select("SELECT * FROM tb_user")
    List<User> getAll();

    @Select("SELECT id,username,password,identity,status FROM tb_user WHERE status = 0")
    List<User> getDeleted();

    //修改密码
    @Update("update tb_user set id=#{id},username=#{username},password=#{password},identity=#{identity}," +
            "sex=#{sex},status=#{status},email=#{email},phone=#{phone},info=#{info} where id=#{id}")
    Integer updatePassword(User user);

    //修改个人信息
    @Update("update tb_user set id=#{id},username=#{username},password=#{password},identity=#{identity}," +
            "sex=#{sex},status=#{status},email=#{email},phone=#{phone},info=#{info} where id=#{id}")
    Integer updateInfo(User user);


}

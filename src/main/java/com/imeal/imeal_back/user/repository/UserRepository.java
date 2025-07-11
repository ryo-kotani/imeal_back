package com.imeal.imeal_back.user.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.imeal.imeal_back.user.entity.User;

@Mapper
public interface UserRepository {
  
  @Select("select * from users where id = #{id}")
  User findById(Integer id);

  @Insert("insert into users (name, email, password) values (#{name}, #{email}, #{password})")
  void insert(User user);
}

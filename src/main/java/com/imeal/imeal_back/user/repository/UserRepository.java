package com.imeal.imeal_back.user.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.imeal.imeal_back.user.entity.User;

@Mapper
public interface UserRepository {
  
  @Select("select * from users where id = #{id}")
  User findById(Integer id);

  @Insert("insert into users (name, email, password) values (#{name}, #{email}, #{password})")
  @Options(useGeneratedKeys=true, keyProperty="id")
  void insert(User user);

  // ユーザー認証用メソッド
  @Select("select * from users where email = #{email}")
  User findByEmail(String email);

  // テスト用メソッド
  @Select("select count(*) from users")
  Integer count();

  @Delete("delete from users")
  void deleteAll();
}

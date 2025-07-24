package com.imeal.imeal_back.base.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.imeal.imeal_back.base.entity.Base;

@Mapper
public interface BaseRepository {
  @Select("select b.*, l.lat, l.lon from bases b join locations l on b.location_id = l.id")
  @Results(value={
    @Result(column="location_id", property="location.id"),
    @Result(column="lat", property="location.lat"),
    @Result(column="lon", property="location.lon")
  })
  List<Base> findAll();

  // テスト用メソッド
  @Insert("insert into bases (name, location_id) values (#{name}, #{location.id})")
  @Options(useGeneratedKeys=true, keyProperty="id")
  void insert(Base base);

  @Select("SELECT b.*, l.lat, l.lon FROM bases b INNER JOIN locations l ON b.location_id=l.id WHERE b.id = #{id}")
  @Results(value={
    @Result(column="lat", property="location.lat"),
    @Result(column="lon", property="location.lon")
  })
  Base findById(Integer id);
}

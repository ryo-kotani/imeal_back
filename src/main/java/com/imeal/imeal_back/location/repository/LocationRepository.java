package com.imeal.imeal_back.location.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import com.imeal.imeal_back.location.entity.Location;

@Mapper
public interface LocationRepository {
  @Insert("INSERT INTO locations (lat, lon) VALUES(#{lat}, #{lon})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Location location);

  @Update("UPDATE locations SET lat = #{lat}, lon = #{lon} WHERE id = #{id}")
  void updateLocation(Location location);
}


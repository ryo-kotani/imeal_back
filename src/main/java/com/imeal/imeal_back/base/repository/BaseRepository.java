package com.imeal.imeal_back.base.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.imeal.imeal_back.base.entity.Base;

@Mapper
public interface BaseRepository {
  List<Base> findAll();
}

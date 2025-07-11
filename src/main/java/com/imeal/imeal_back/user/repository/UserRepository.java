package com.imeal.imeal_back.user.repository;

import com.imeal.imeal_back.user.entity.User;

public interface UserRepository {
  User findById(Integer id);
  void insert(User user);
}

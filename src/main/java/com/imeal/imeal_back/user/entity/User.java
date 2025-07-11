package com.imeal.imeal_back.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
  Integer id;
  String name;
  String email;
  String password;
}

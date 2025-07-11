package com.imeal.imeal_back.base.entity;

import com.imeal.imeal_back.location.entity.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Base {
  Integer id;
  String name;
  Location location;
}

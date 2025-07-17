package com.imeal.imeal_back.location.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
  Integer id;
  // Integer -> BigDecimal(小数点を含む数字型)変更
  BigDecimal lat;
  BigDecimal lon;
}

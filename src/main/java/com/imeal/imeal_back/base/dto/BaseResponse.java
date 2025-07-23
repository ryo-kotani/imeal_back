package com.imeal.imeal_back.base.dto;

import com.imeal.imeal_back.location.dto.LocationResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
  Integer id;
  String name;
  LocationResponse location;
}

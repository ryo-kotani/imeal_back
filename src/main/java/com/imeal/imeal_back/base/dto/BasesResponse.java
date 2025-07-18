package com.imeal.imeal_back.base.dto;

import java.util.List;

import com.imeal.imeal_back.base.entity.Base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasesResponse {
  List<Base> bases;
}

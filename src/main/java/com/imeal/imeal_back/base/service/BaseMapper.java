package com.imeal.imeal_back.base.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.dto.BasesResponse;
import com.imeal.imeal_back.base.entity.Base;

@Component
public class BaseMapper {
  public BasesResponse toResponse(List<Base> bases) {
    BasesResponse response = new BasesResponse();
    response.setBases(bases);
    return response;
  }
}

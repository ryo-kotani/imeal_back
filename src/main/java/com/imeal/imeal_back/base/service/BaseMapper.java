package com.imeal.imeal_back.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.dto.BaseResponse;
import com.imeal.imeal_back.base.dto.BasesResponse;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.location.dto.LocationResponse;
import com.imeal.imeal_back.location.service.LocationMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseMapper {
  private final LocationMapper locationMapper;
  
  public BaseResponse toBaseResponse(Base base) {
    if (base == null) {
      return null;
    }

    BaseResponse response = new BaseResponse();
    response.setId(base.getId());
    response.setName(base.getName());

    LocationResponse locationResponse = locationMapper.toResponse(base.getLocation());
    response.setLocation(locationResponse);

    return response;
  }

  public BasesResponse toBasesResponse(List<Base> bases) {
    List<BaseResponse> baseResponses = new ArrayList<>();
    for (Base base: bases) {
      baseResponses.add(toBaseResponse(base));
    }

    BasesResponse response = new BasesResponse();
    response.setBases(baseResponses);
    return response;
  }
}

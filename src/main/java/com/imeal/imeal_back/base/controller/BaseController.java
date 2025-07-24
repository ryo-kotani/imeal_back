package com.imeal.imeal_back.base.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.base.dto.BaseResponse;
import com.imeal.imeal_back.base.service.BaseService;

import lombok.RequiredArgsConstructor;


@RestController
// "/" → ""に変更
@RequestMapping("/api/bases")
@RequiredArgsConstructor
public class BaseController {
  private final BaseService baseService;
  
  @GetMapping()
  public List<BaseResponse> getBases() {
    return baseService.getBases();
  }
  
}

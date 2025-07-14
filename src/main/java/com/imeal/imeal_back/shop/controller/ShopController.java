package com.imeal.imeal_back.shop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.shop.dto.ShopListResponse;
import com.imeal.imeal_back.shop.service.ShopService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

  private final ShopService shopService;

  @GetMapping("")
  public ResponseEntity<ShopListResponse> getShops(@RequestParam("baseId") Integer baseId) {

    ShopListResponse response = shopService.getShops(baseId);

    return ResponseEntity.ok(response);
  }
  
}

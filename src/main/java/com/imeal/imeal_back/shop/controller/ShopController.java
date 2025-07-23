package com.imeal.imeal_back.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.common.validation.ValidationGroups;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopsResponse;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
import com.imeal.imeal_back.shop.service.ShopService;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

  private final ShopService shopService;

  @GetMapping
  public ResponseEntity<ShopsResponse> getShops(@RequestParam("baseId") Integer baseId) {

    ShopsResponse response = shopService.getShops(baseId);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ShopResponse> createShop(@Validated(ValidationGroups.Group.class) @RequestBody ShopCreateRequest request) {
    
    ShopResponse createdShop = shopService.createShop(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdShop);
  }
  
  @GetMapping("/{shopId}/reviews")
  public ShopResponse getShopWithReviews(@PathVariable("shopId") Integer shopId) {
    return shopService.getShopWithReviews(shopId);
  }

  @GetMapping("/{shopId}")
  public ShopResponse getShop(@PathVariable("shopId") Integer shopId) {
    return shopService.getShop(shopId);
  }
  
  
  @DeleteMapping("/{shopId}")
  public ResponseEntity<Void> deleteShop(@PathVariable("shopId") Integer shopId) {
    shopService.deleteShop(shopId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{shopId}")
  public ShopResponse updateShop(@PathVariable("shopId") Integer shopId, @Validated(ValidationGroups.Group.class) @RequestBody ShopUpdateRequest request) {
    return shopService.updateShop(shopId, request);
  }
}


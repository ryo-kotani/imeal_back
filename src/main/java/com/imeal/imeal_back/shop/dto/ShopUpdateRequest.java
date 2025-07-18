package com.imeal.imeal_back.shop.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.Latitude;
import com.imeal.imeal_back.common.validation.Longitude;
import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopUpdateRequest {

  @NotBlank(message = "{error.imgPath.notblank}", groups = ValidationGroups.First.class)
  @Length(max = 256, message = "{error.imgPath.length}", groups = ValidationGroups.Second.class)
  private String url;
  
  @NotBlank(message = "{error.imgPath.notblank}", groups = ValidationGroups.First.class)
  @Length(max = 256, message = "{error.imgPath.length}", groups = ValidationGroups.Second.class)
  private String name;
  
  @NotBlank(message = "{error.imgPath.notblank}", groups = ValidationGroups.First.class)
  @Length(max = 256, message = "{error.imgPath.length}", groups = ValidationGroups.Second.class)
  private String address;
  
  @NotNull(message = "{error.amount.notnull}", groups = ValidationGroups.First.class)
  private Integer distance;
  
  @NotNull(message = "{error.amount.notnull}", groups = ValidationGroups.First.class)
  private Integer minutes;
  
  @NotNull(message="緯度は必須です", groups = ValidationGroups.First.class)
  @Latitude(groups = ValidationGroups.Second.class)
  private BigDecimal locationLat;
  
  @NotNull(message="経度は必須です", groups = ValidationGroups.First.class)
  @Longitude(groups = ValidationGroups.Second.class)
  private BigDecimal locationLon;
}

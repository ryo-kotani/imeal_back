package com.imeal.imeal_back.shop.dto;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.ValidationGroups;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
//バリデーション後で直す
public class ShopCreateRequest {

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
  
  @NotNull(message = "{error.amount.notnull}", groups = ValidationGroups.First.class)
  private Integer baseId;
  
  @NotNull(message = "locationは必須です", groups = ValidationGroups.First.class)
  @Valid
  private LocationCreateRequest location;
}

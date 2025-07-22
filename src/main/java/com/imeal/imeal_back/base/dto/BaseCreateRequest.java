package com.imeal.imeal_back.base.dto;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCreateRequest {
  
  @NotBlank(message="{error.name.notblank}", groups=ValidationGroups.First.class)
  @Length(message="{error.name.length}", max=256, groups=ValidationGroups.Second.class)
  String name;

  @NotNull(message="{error.locationId.notnull}", groups=ValidationGroups.First.class)
  Integer locationId;
}

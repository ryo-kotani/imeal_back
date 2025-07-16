package com.imeal.imeal_back.review.dto;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * {この箇所}はValidationMessages.propertiesで関連付けた値が入る
 */
@Getter
@Setter
public class ReviewCreateRequest {

  @NotBlank(message = "{error.img.notblank}", groups = ValidationGroups.First.class)
  @Length(max = 256, message = "{error.img.length}", groups = ValidationGroups.Second.class)
  String img;

  @NotBlank(message = "{error.comment.notblank}", groups = ValidationGroups.First.class)
  @Length(max = 256, message = "{error.comment.length}", groups = ValidationGroups.Second.class)
  String comment;

  @NotNull(message = "{error.amount.notnull}", groups = ValidationGroups.First.class)
  Integer amount;

  @NotNull(message = "{error.evaluation.notnull}", groups = ValidationGroups.First.class)
  @Min(value = 0, groups = ValidationGroups.Second.class, message = "{error.evaluation.min}")
  @Max(value = 5, groups = ValidationGroups.Second.class, message = "{error.evaluation.max}")
  Integer evaluation;

  @NotNull(message = "{error.shopId.notnull}", groups = ValidationGroups.First.class)
  Integer shopId;
}

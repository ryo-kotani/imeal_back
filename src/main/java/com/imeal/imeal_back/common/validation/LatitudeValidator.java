package com.imeal.imeal_back.common.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, BigDecimal> {

  // 比較用の定数を定義しておくと効率的
  private static final BigDecimal MIN_LATITUDE = new BigDecimal("-90");
  private static final BigDecimal MAX_LATITUDE = new BigDecimal("90");
  private static final int MAX_SCALE = 6; // 小数点以下6桁まで

  @Override
  public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
    // nullの場合はバリデーション対象外とする（必須チェックは @NotNull で）
    if (value == null) {
      return true;
    }

    // 1. 範囲チェック (-90 <= value <= 90)
    boolean isRangeValid = value.compareTo(MIN_LATITUDE) >= 0 && value.compareTo(MAX_LATITUDE) <= 0;
    if (!isRangeValid) {
      return false;
    }

    // 2. 小数点以下の桁数チェック (6桁以下)
    boolean isScaleValid = value.scale() <= MAX_SCALE;
    if (!isScaleValid) {
      return false;
    }
    
    return true;
    }
}

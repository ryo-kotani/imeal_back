package com.imeal.imeal_back.common.validation;

import com.imeal.imeal_back.user.dto.UserCreateRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValueValidator implements ConstraintValidator<PasswordValue, UserCreateRequest> {

  /**
   * パスワード検証
   * @param value 検索対象
   * @param context バリデーションコンテキスト
   * @return 検証OK: true, 検証NG: false
   */
  @Override
  public boolean isValid(UserCreateRequest value, ConstraintValidatorContext context) {

    // nullの場合は@NotNullに任せる
    if (value == null || value.getPassword() == null || value.getPasswordConfirmation() == null) return true;

    // passwordとpasswordConfirmationが一致していればtrue
    if (value.getPassword().equals(value.getPasswordConfirmation())) {
      return true;
    } else {
      return false;
    }
  }
}

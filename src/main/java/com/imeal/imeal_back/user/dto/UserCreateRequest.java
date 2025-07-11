package com.imeal.imeal_back.user.dto;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.PasswordValue;
import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@PasswordValue(groups=ValidationGroups.Third.class)
public class UserCreateRequest {

  @NotBlank(message="nameが空", groups=ValidationGroups.First.class)
  @Length(min=6, max=256, message="nameは6文字以上256文字以下", groups=ValidationGroups.Second.class)
  String name;

  @NotBlank(message="emailが空", groups=ValidationGroups.First.class)
  @Email(message="emailが不正の型", groups=ValidationGroups.Second.class)
  @Length(min=6, max=256, message="emailは6文字以上256文字以下", groups=ValidationGroups.Third.class)
  String email;

  @NotBlank(message="passwordが空", groups=ValidationGroups.First.class)
  @Length(min=6, max=256, message="passwordは6文字以上256文字以下", groups=ValidationGroups.Second.class)
  String password;

  @NotBlank(message="passwordConfirmationが空", groups=ValidationGroups.First.class)
  @Length(min=6, max=256, message="passwordConfirmationは6文字以上256文字以下", groups=ValidationGroups.Second.class)
  String passwordConfirmation;
}

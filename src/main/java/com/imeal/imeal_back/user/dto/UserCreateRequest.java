package com.imeal.imeal_back.user.dto;

import org.hibernate.validator.constraints.Length;

import com.imeal.imeal_back.common.validation.PasswordValue;
import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordValue(message = "{error.password.mismatch}", groups = ValidationGroups.Third.class)
public class UserCreateRequest {

    @NotBlank(message = "{error.name.notblank}", groups = ValidationGroups.First.class)
    @Length(message = "{error.name.length}", max = 10, groups = ValidationGroups.Second.class)
    String name;

    @NotBlank(message = "{error.email.notblank}", groups = ValidationGroups.First.class)
    @Email(message = "{error.email.format}", groups = ValidationGroups.Second.class)
    @Length(message = "{error.email.length}", min = 6, max = 256, groups = ValidationGroups.Third.class)
    String email;

    @NotBlank(message = "{error.password.notblank}", groups = ValidationGroups.First.class)
    @Length(message = "{error.password.length}", min = 6, max = 256, groups = ValidationGroups.Second.class)
    String password;

    @NotBlank(message = "{error.password.notblank}", groups = ValidationGroups.First.class) // passwordと同じキーを再利用
    @Length(message = "{error.password.length}", min = 6, max = 256, groups = ValidationGroups.Second.class) // passwordと同じキーを再利用
    String passwordConfirmation;
}
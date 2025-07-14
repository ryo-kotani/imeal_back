package com.imeal.imeal_back.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy=PasswordValueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValue {
  
  // デフォルトメッセージ
  String message() default "";

  // バリデーショングループを付与
  Class<?>[] groups() default {};

  // 引数を指定
  Class<? extends Payload>[] payload() default {};
}

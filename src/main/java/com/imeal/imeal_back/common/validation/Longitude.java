package com.imeal.imeal_back.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = LongitudeValidator.class) // バリデーションロジックのクラスを指定
@Target({ ElementType.FIELD }) // アノテーションをフィールドに付与可能にする
@Retention(RetentionPolicy.RUNTIME) // 実行時にアノテーション情報をVMが保持するようにする
public @interface Longitude {
  // バリデーションエラー時のデフォルトメッセージ
  String message() default "経度が有効な範囲（-180 ～ 180）ではありません。";

  // 特定の状況でバリデーションを使い分けるためのグループ機能
  Class<?>[] groups() default {};

  // メタ情報を定義するためのPayload
  Class<? extends Payload>[] payload() default {};
}
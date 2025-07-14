package com.imeal.imeal_back.common.validation;

import jakarta.validation.GroupSequence;

public interface ValidationGroups {
  // バリデーションのグルーピング
  interface First{};
  interface Second{};
  interface Third{};

  // バリデーショングループを適用
  @GroupSequence({First.class, Second.class, Third.class})
  interface Group{};
}

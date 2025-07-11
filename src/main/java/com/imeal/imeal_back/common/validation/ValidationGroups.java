package com.imeal.imeal_back.common.validation;

import jakarta.validation.GroupSequence;

public interface ValidationGroups {
  // バリデーションのグルーピング
  class First{};
  class Second{};
  class Third{};

  // バリデーショングループを適用
  @GroupSequence({First.class, Second.class, Third.class})
  class User{};
}

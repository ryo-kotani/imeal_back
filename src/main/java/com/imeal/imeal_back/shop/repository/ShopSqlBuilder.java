package com.imeal.imeal_back.shop.repository;

import org.apache.ibatis.jdbc.SQL;

public class ShopSqlBuilder {
  public String buildSearchSql(Integer baseId) {
    return new SQL() {{
      SELECT("*");
      FROM("shops");
      if (baseId != null) {
        WHERE ("base_id = #{baseId}");
      }
    }}.toString();
  }
}

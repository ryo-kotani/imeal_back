package com.imeal.imeal_back.shop.repository;

import org.apache.ibatis.jdbc.SQL;

public class ShopSqlBuilder {
  public String buildSearchSql(Integer baseId) {
    return new SQL() {{
      SELECT("s.*, l.id ,l.lat, l.lon");
      FROM("shops s");
      INNER_JOIN("locations l ON s.location_id = l.id"); 
      if (baseId != null) {
        WHERE ("s.base_id = #{baseId}");
      }
    }}.toString();
  }
}

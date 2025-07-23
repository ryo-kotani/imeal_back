package com.imeal.imeal_back.shop.repository;

import org.apache.ibatis.jdbc.SQL;

//findByXのsql文を作成している
public class ShopSqlBuilder {
  public String buildSearchSql(Integer baseId) {
    return new SQL() {{
      SELECT("s.*, l.id ,l.lat, l.lon");
      FROM("shops s");
      LEFT_OUTER_JOIN("locations l ON s.location_id = l.id"); //shopsとlocationを左外部結合
      if (baseId != null) {
        WHERE ("s.base_id = #{baseId}"); //baseIdの拠点にある店舗に絞り込む
      }
    }}.toString();
  }
}

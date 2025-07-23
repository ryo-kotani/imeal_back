package com.imeal.imeal_back.shop.repository;

import org.apache.ibatis.jdbc.SQL;

//findByXのsql文を作成している
public class ShopSqlBuilder {
  public String buildSearchSql(Integer baseId) {
    return new SQL() {{
      SELECT(// Shopの情報
            "s.id", "s.url", "s.name", "s.address", "s.distance", "s.minutes",
            // Shopに紐づくLocationの情報
            "s.location_id", 
            "shop_loc.lat AS shop_lat", 
            "shop_loc.lon AS shop_lon",
            // Baseの情報
            "s.base_id",
            "b.id AS base_id_alias",
            "b.name AS base_name",
            // Baseに紐づくLocationの情報
            "b.location_id AS base_location_id",
            "base_loc_lat AS base_lat",
            "base_loc_lon AS base_lon");
      FROM("shops s");
      // ShopのLocationをJOIN (エイリアス: shop_loc)
      LEFT_OUTER_JOIN("locations shop_loc ON s.location_id = shop_loc.id");
      // BaseをJOIN
      LEFT_OUTER_JOIN("bases b ON s.base_id = b.id");
      // BaseのLocationをJOIN (エイリアス: base_loc)
      LEFT_OUTER_JOIN("locations base_loc ON b.location_id = base_loc.id");
      if (baseId != null) {
        WHERE ("s.base_id = #{baseId}"); //baseIdの拠点にある店舗に絞り込む
      }
    }}.toString();
  }
}

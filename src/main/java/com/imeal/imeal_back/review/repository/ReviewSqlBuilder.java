package com.imeal.imeal_back.review.repository;

import org.apache.ibatis.jdbc.SQL;

public class ReviewSqlBuilder {
public String buildSearchSql(Integer baseId, String sort, Integer limit) {
    SQL sql = new SQL() {{
      SELECT("s.url AS shop_url", "s.name as shop_name", "s.address as shop_address", "s.distance as shop_distance", "s.minutes as shop_minutes", "s.location_id AS shop_location_id", "s.location_lat AS shop_location_lat", "s.location_lon AS shop_location_lon", 
              "r.*", 
              "u.id AS user_id", "u.name AS user_name");
      FROM("reviews r");
      LEFT_OUTER_JOIN("shops s ON s.id = r.shop_id"); 
      LEFT_OUTER_JOIN("users u ON u.id = r.user_id");
      if (baseId != null) {
        WHERE ("base_id = #{baseId}");
      }
      if (sort != null && !sort.trim().isEmpty()) {
        String direction = sort.startsWith("-") ?  "DESC" : "ASC"; //降順の方向に並び替える
        String field = sort.startsWith("-") ? sort.substring(1) : sort; //先頭の　"-"　を削除
        String dbColumn; //SQLインジェクション対策として、並び替えを許可するカラムを明示的に指定する
        switch (field) {
            case "createdAt" :
                dbColumn = "r.created_at";
                break;
            default :
                dbColumn = null;
                break;
        }
      if (dbColumn != null) {
        ORDER_BY(dbColumn + " " + direction);
      }
    } else {
      ORDER_BY("r.created_at DESC");
    }
    }};
  
  // 文字列に変換した後にLIMIT句を追加
  String sqlString = sql.toString();
  if (limit != null) {
    sqlString += " LIMIT #{limit}";
  }

  return sqlString;
  }
}


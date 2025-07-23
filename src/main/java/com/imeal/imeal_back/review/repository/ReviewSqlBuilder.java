package com.imeal.imeal_back.review.repository;

import org.apache.ibatis.jdbc.SQL;

public class ReviewSqlBuilder {
public String buildSearchSql(Integer baseId, String sort, Integer limit) {
    SQL sql = new SQL() {{
      SELECT("s.*, r.*");
      FROM("reviews r");
      INNER_JOIN("shops s ON s.id = r.shop_id"); 
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


package com.imeal.imeal_back.review.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.imeal.imeal_back.review.entity.Review;

@Mapper
public interface ReviewRepository {
  /**
   * baseId, sort, limitの条件をもとにreview, user, shop, shopに紐づくlocationを取得する
   * @param baseId
   * @param sort
   * @param limit
   * @return reviewのリスト（Optionalをつけることで存在しない場合はthrow必須になる）
   */
  @SelectProvider(type = ReviewSqlBuilder.class, method = "buildSearchSql")
  @Results({
    @Result(column = "user_id", property = "user.id"),
    @Result(column = "user_name", property = "user.name"),
    @Result(column = "shop_id", property = "shop.id"),
    @Result(column = "shop_url", property = "shop.url"),
    @Result(column = "shop_name", property = "shop.name"),
    @Result(column = "shop_address", property = "shop.address"),
    @Result(column = "shop_distance", property = "shop.distance"),
    @Result(column = "shop_minutes", property = "shop.minutes"),
    @Result(column = "shop_location_id", property = "shop.location.id"),
    @Result(column = "shop_location_lat", property = "shop.location.lat"),
    @Result(column = "shop_location_lon", property = "shop.location.lon"),
  })
  Optional<List<Review>> findByX(@Param("baseId")Integer baseId, @Param("sort")String sort, @Param("limit")Integer limit);

  @Insert("insert into reviews (img_path, comment, amount, evaluation, shop_id, user_id) values (#{imgPath}, #{comment}, #{amount}, #{evaluation}, #{shop.id}, #{user.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Review review);

  @Update("update reviews set img_path = #{imgPath}, comment = #{comment}, amount = #{amount}, evaluation = #{evaluation} where id = #{id}")
  void update(Review review);

  /**
   * idに紐づくReviewレコードを削除する
   * 
   * @param id 対象ReviewレコードのID
   */
  @Delete("delete from reviews where id = #{id}")
  Integer delete(Integer id);

  // user情報も一緒に取得する
  @Select("select r.*, u.name as user_name from reviews r join users u on r.user_id = u.id where r.shop_id = #{shopId}")
  @Results(value = {
    @Result(column = "user_id", property = "user.id"),
    @Result(column = "user_name", property = "user.name")
  })
  List<Review> findWithUserByShopId(Integer shopId);

  /**
   * user, shop, location情報も一緒に取得する
   * 
   * @param id reviewのid
   * @return Reviewエンティティ
   */
  @Select("select r.*, s.url as shop_url, s.name as shop_name, s.address as shop_address, s.distance as shop_distance, s.minutes as shop_minutes, s.location_id, s.location_lat, s.location_lon, u.name as user_name from reviews r join (select s.*, l.lat as location_lat, l.lon as location_lon from shops s join locations l on s.location_id = l.id) s on r.shop_id = s.id join users u on r.user_id = u.id where r.id = #{id}")
  @Results(value = {
    @Result(column = "id", property = "id"),
    @Result(column = "shop_id", property = "shop.id"),
    @Result(column = "shop_url", property = "shop.url"),
    @Result(column = "shop_name", property = "shop.name"),
    @Result(column = "shop_address", property = "shop.address"),
    @Result(column = "shop_distance", property = "shop.distance"),
    @Result(column = "shop_minutes", property = "shop.minutes"),
    @Result(column = "location_id", property = "shop.location.id"),
    @Result(column = "location_lat", property = "shop.location.lat"),
    @Result(column = "location_lon", property = "shop.location.lon"),
    @Result(column = "user_id", property = "user.id"),
    @Result(column = "user_name", property = "user.name")
  })
  Review findWithShopLocationUserById(Integer id);

  /**
   * shop,location情報を一緒に取得する
   * 
   * @param id reviewのid
   * @return Reviewエンティティ
   */
  @Select("""
          SELECT
            -- reviewsテーブル
      	    r.*,
            -- 関連テーブルのカラム
            s.id AS shop_id,
            s.url AS shop_url,
            s.name AS shop_name,
            s.address AS shop_address,
            s.distance AS shop_distance,
            s.minutes AS shop_minutes,
            l.id AS location_id,
            l.lat AS location_lat,
            l.lon AS location_lon
          FROM
            reviews r
          LEFT JOIN
            shops s ON r.shop_id = s.id
          LEFT JOIN
            locations l ON s.location_id = l.id
          WHERE
            r.id = #{id}
          """)
  @Results(value = {
      @Result(column = "id", property = "id"),
      @Result(column = "shop_id", property = "shop.id"),
      @Result(column = "shop_url", property = "shop.url"),
      @Result(column = "shop_name", property = "shop.name"),
      @Result(column = "shop_address", property = "shop.address"),
      @Result(column = "shop_distance", property = "shop.distance"),
      @Result(column = "shop_minutes", property = "shop.minutes"),
      @Result(column = "location_id", property = "shop.location.id"),
      @Result(column = "location_lat", property = "shop.location.lat"),
      @Result(column = "location_lon", property = "shop.location.lon"),
  })
  Review findWithShopLocationById(Integer id);

  /**
   * テスト用カウント機能
   * 
   * @return Reviewテーブルのレコード数
   */
  @Select("select count(*) from reviews")
  Integer count();
}

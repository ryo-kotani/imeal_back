package com.imeal.imeal_back.shop.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.imeal.imeal_back.shop.entity.Shop;

@Mapper
public interface ShopRepository {
  //拠点に紐づく店舗の情報を取得する
  @SelectProvider(type = ShopSqlBuilder.class, method = "buildSearchSql") //sql文の生成はShopSqlBuilderで行う
  @Results(value={
    @Result(column = "location_id", property = "location.id"),
    @Result(column = "lat", property = "location.lat"),
    @Result(column = "lon", property = "location.lon")
  })
  List<Shop> findByX(@Param("baseId")Integer baseId);

  // Shopに紐づくLocation, Reviews, Reviewに紐づくUserを取得する
  @Select("select s.*, l.lat, l.lon from shops s join locations l on s.location_id = l.id where s.id = #{id}")
  @Results(value = {
    @Result(column = "id", property = "id"),
    @Result(column = "location_id", property = "location.id"),
    @Result(column = "lat", property = "location.lat"),
    @Result(column = "lon", property = "location.lon"),
    @Result(column = "id", property = "reviews", many = @Many(select = "com.imeal.imeal_back.review.repository.ReviewRepository.findWithUserByShopId"))
  })
  Shop findByIdWithReviews(Integer id);

  @Select("SELECT * FROM shops WHERE id = #{id}")
  Shop findById(Integer id);

  @Insert("INSERT INTO shops (url, name, address, distance, minutes, base_id, location_id) VALUES(#{url}, #{name}, #{address}, #{distance}, #{minutes}, #{base.id}, #{location.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Shop shop);
  
  @Update("UPDATE shops SET url = #{url}, name = #{name}, address = #{address}, distance = #{distance}, minutes = #{minutes}, location_id = #{location.id} WHERE id = #{id}")
  void update(Shop shop);

  @Delete("DELETE FROM shops WHERE id = #{id}")
  int delete(Integer id);

  @Select("SELECT COUNT(*) FROM shops")
  long count();
}

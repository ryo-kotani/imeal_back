// package com.imeal.imeal_back.helper.request;

// import java.math.BigDecimal;

// import org.springframework.stereotype.Component;

// import com.imeal.imeal_back.helper.faker.LocationFaker;
// import com.imeal.imeal_back.helper.faker.ShopFaker;
// import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;

// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class ShopUpdateRequestFactory {
//   private final ShopFaker shopFaker;
//   private final LocationFaker locationFaker;

//   // デフォルトのリクエストオブジェクトを生成
//   public ShopUpdateRequest createValidRequest() {
//     return builder().build();
//   }

//   // ビルダーのエントリーポイント
//   public ShopUpdateRequestBuilder builder() {
//     return new ShopUpdateRequestBuilder();
//   }

//   // ランダムなリクエストオブジェクトを生成
//   public class ShopUpdateRequestBuilder {
//     private String name = shopFaker.createName();
//     private String url = shopFaker.createUrl();
//     private String address = shopFaker.createAddress();
//     private Integer distance = shopFaker.createDistance();
//     private Integer minutes = shopFaker.createMinutes();
//     private BigDecimal locationLat = locationFaker.createLat();
//     private BigDecimal locationLon = locationFaker.createLon();

//     public ShopUpdateRequestBuilder withName(String name) {
//       this.name = name;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withUrl(String url) {
//       this.url = url;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withAddress(String address) {
//       this.address = address;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withDistance(Integer distance) {
//       this.distance = distance;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withMinutes(Integer minutes) {
//       this.minutes = minutes;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withLocationLat(BigDecimal locationLat) {
//       this.locationLat = locationLat;
//       return this;
//     }

//     public ShopUpdateRequestBuilder withLocationLon(BigDecimal locationLon) {
//       this.locationLon = locationLon;
//       return this;
//     }

//     public ShopUpdateRequest build() {
//       ShopUpdateRequest request = new ShopUpdateRequest();
      
//       request.setName(name);
//       request.setUrl(url);
//       request.setAddress(address);
//       request.setDistance(distance);
//       request.setMinutes(minutes);
//       request.setLocationLat(locationLat);
//       request.setLocationLon(locationLon);

//       return request;
//     }
//   }
// }

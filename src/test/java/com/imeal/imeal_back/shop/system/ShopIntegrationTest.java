package com.imeal.imeal_back.shop.system;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; // BaseをDBに保存するために必要
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.ImealBackApplication;
import static com.imeal.imeal_back.base.builder.BaseBuilder.aBase;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository; // テスト後のDB変更をロールバック
import static com.imeal.imeal_back.location.builder.LocationBuilder.aLocation;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = ImealBackApplication.class)
@AutoConfigureMockMvc
@Transactional // 各テストメソッドの後にデータベースの変更をロールバック
public class ShopIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;// オブジェクトをJSON形式に変換する

  @Autowired
  private ShopRepository shopRepository;// shop保存

  @Autowired
  private BaseRepository baseRepository; // Base保存

  @Autowired
  private LocationRepository locationRepository;// locaiton保存

  private Base testBase;
  private Location testLocation;
  private Shop testShop;

  private ShopCreateRequest createValidShopCreateRequest(Integer baseId) {
    ShopCreateRequest request = new ShopCreateRequest();
    request.setName("テスト店舗");
    request.setUrl("http://example.com");
    request.setAddress("東京都テスト区1-1-1");
    request.setDistance(100);
    request.setMinutes(5);
    request.setBaseId(baseId);
    LocationCreateRequest location = new LocationCreateRequest();
    location.setLat(new BigDecimal("35.681236"));
    location.setLon(new BigDecimal("139.767125"));
    request.setLocation(location);
    return request;
  }

  // 各テストの前に実行
  @BeforeEach
  void setUp() {
    // locationの作成・保存
    Location createLocation = aLocation().withLat("35.123").withLon("139.0000").build();
    locationRepository.insert(createLocation);
    this.testLocation = createLocation;

    // Baseの作成・保存
    Base createBase = aBase().withName("テスト拠点1").withLocation(testLocation).build();
    baseRepository.insert(createBase);
    this.testBase = createBase;

    

    // 店舗データを作成・保存
    Shop shop = new Shop();
    shop.setName("既存のテスト店舗");
    shop.setUrl("http://existing.com");
    shop.setAddress("東京都既存区1-2-3");
    shop.setDistance(3);
    shop.setMinutes(10);

    shop.setBase(testBase);
    shop.setLocation(testLocation);

    shopRepository.insert(shop);
    this.testShop = shop;

  }

  // --- ここから各APIのテストを記述 ---
  @Nested
  class 店舗を作成する場合 {

    @Test
    void 正常なリクエストで店舗を作成できる() throws Exception {
      // 1. 準備 (Arrange)
      ShopCreateRequest request = createValidShopCreateRequest(testBase.getId());
      long countBefore = shopRepository.count(); 

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(post("/api/shops")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").exists())
          .andExpect(jsonPath("$.name").value("テスト店舗"))
          .andExpect(jsonPath("$.location.lat").value(35.681236));

      // DBの状態変化を確認
      assertEquals(countBefore + 1, shopRepository.count());
    }

    @Test
    void 不正なリクエストでは店舗作成に失敗し400を返す() throws Exception {
      // 1. 準備 (Arrange)
      // @Valid を使ったバリデーションをDTOに追加した場合のテスト例
      ShopCreateRequest request = createValidShopCreateRequest(testBase.getId());
      request.setName(""); // 不正なデータ（例：名前が空）

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(post("/api/shops")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest()); // バリデーションエラーなので400
    }
  }
  @Nested
  class 店舗を更新する場合 {

    @Test
    void 存在する店舗の情報を更新できる() throws Exception {
      // 1. 準備 (Arrange)
      ShopUpdateRequest request = new ShopUpdateRequest();
      request.setName("更新された店舗名");
      request.setUrl(testShop.getUrl());
      request.setAddress(testShop.getAddress());
      request.setDistance(testShop.getDistance());
      request.setMinutes(testShop.getMinutes());
      request.setLocation(new LocationCreateRequest());
      request.getLocation().setLat(BigDecimal.valueOf(35.657067));
      request.getLocation().setLon(BigDecimal.valueOf(139.758693));

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(put("/api/shops/" + testShop.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value("更新された店舗名"));
    }

    @Test
    void 存在しない店舗を更新しようとすると404エラーが返る() throws Exception {
      // 1. 準備 (Arrange)
      ShopUpdateRequest request = new ShopUpdateRequest();
      request.setName("更新テスト");
      request.setUrl(testShop.getUrl());
      request.setAddress(testShop.getAddress());
      request.setDistance(testShop.getDistance());
      request.setMinutes(testShop.getMinutes());
      request.setLocation(new LocationCreateRequest());
      request.getLocation().setLat(BigDecimal.valueOf(35.657067));
      request.getLocation().setLon(BigDecimal.valueOf(139.758693));

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(put("/api/shops/9999") // 存在しないID
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isNotFound());
    }
  }
}

package com.imeal.imeal_back.shop;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.helper.lib.TestAuthHelper;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.BaseTestDataFactory;
import com.imeal.imeal_back.helper.testData.LocationTestDataFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

public class ShopUpdateIntegrationTest {
  @Nested
  class 店舗を更新する場合 {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BaseTestDataFactory baseTestDataFactory;
  @Autowired
  private LocationTestDataFactory locationTestDataFactory;
  @Autowired
  private ShopTestDataFactory shopTestDataFactory;
  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;
  @Autowired
  private UserTestDataFactory userTestDataFactory;
  @Autowired
  private TestAuthHelper testAuthHelper;

  private String loginEmail;
  private String loginPassword;

  private Integer baseId;
  private Integer locationId;

  private Shop testShop;

  @BeforeEach
  public void setUp() {
    // base作成
    Base base = baseTestDataFactory.createDefaultBase();
    baseId = base.getId();

    // location作成
    Location location = locationTestDataFactory.createDefaultLocation();
    locationId = location.getId();

    // user作成
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();
    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();
    userTestDataFactory.builder().withEmail(loginEmail).withPassword(loginPassword).buildAndPersist();

    // shop作成
    testShop = shopTestDataFactory.builder().withBase(base).buildAndPersist();
  }

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

package com.imeal.imeal_back.shop.system;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.factory.UserCreateRequestFactory;


@ActiveProfiles("test")
@SpringBootTest
@Transactional //テスト後にデータベースの状態をロールバックする
public class ShopIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  private UserCreateRequest request;

  @BeforeEach
  public void setUp() {
    request = UserCreateRequestFactory.createValidRequest();
  }

  @Nested
  class 店舗リストが取得できるとき {
    @Test
    public void 有効な拠点IDを指定すると店舗リストが正常に返される() throws Exception {
      // Given: テストデータは `data.sql` で投入されている前提 (後述)
      int baseId = 1;

      // When & Then
      mockMvc.perform(get("/api/shops")
                        .param("baseId", String.valueOf(baseId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shops").isArray())
                .andExpect(jsonPath("$.shops", hasSize(2))) // baseId=1 の店舗は2件
                .andExpect(jsonPath("$.shops[0].id", is(101)))
                .andExpect(jsonPath("$.shops[0].name", is("テスト店舗A")))
                .andExpect(jsonPath("$.shops[1].id", is(102)))
                .andExpect(jsonPath("$.shops[1].name", is("テスト店舗B")));
    }
  }

}

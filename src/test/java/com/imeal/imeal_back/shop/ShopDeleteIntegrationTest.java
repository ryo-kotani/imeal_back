package com.imeal.imeal_back.shop;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.BaseTestDataFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes = ImealBackApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ShopDeleteIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ShopRepository shopRepository;// shop保存

  @Autowired
  private ShopTestDataFactory shopTestDataFactory;

  @Autowired
  private BaseTestDataFactory baseTestDataFactory;

  @Autowired
  private UserTestDataFactory userTestDataFactory;
  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;

  private Base base1;
  private Base base2;

  private Shop shop1WithBase1;
  private Shop shop2WithBase1;
  private Shop shopWithBase2;

  private String loginEmail;
  private String loginPassword;

  @BeforeEach
  void setUp() {
    // user作成
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();
    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();
    userTestDataFactory.builder().withEmail(loginEmail).withPassword(loginPassword).buildAndPersist();
    
    // base作成
    base1 = baseTestDataFactory.createDefaultBase();
    base2 = baseTestDataFactory.createDefaultBase();

    // shop作成
    shop1WithBase1 = shopTestDataFactory.builder().withBase(base1).buildAndPersist();
    shop2WithBase1 = shopTestDataFactory.builder().withBase(base1).buildAndPersist();
    shopWithBase2 = shopTestDataFactory.builder().withBase(base2).buildAndPersist();
  }

  @Nested
  class 削除できる場合 {

    @Test
    void 存在する店舗を削除できる() throws Exception {
      // 1. 準備 (Arrange) - setUpで作成したtestShopのIDを使用
      long countBefore = shopRepository.count();

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(delete("/api/shops/" + shop1WithBase1.getId()))
          .andExpect(status().isNoContent()); // 204 No Content

      // DBの状態変化を確認
      assertEquals(countBefore - 1, shopRepository.count());
      Optional<Shop> found = Optional.ofNullable(shopRepository.findById(shop1WithBase1.getId()));
      assertTrue(found.isEmpty());
    }
  }
  @Nested
  class 削除できない場合 {
    @Test
    void 存在しないIDを指定すると404エラーが返る() throws Exception {
      mockMvc.perform(delete("/api/shops/9999"))
          .andExpect(status().isNotFound());
    }
  }
}

package com.imeal.imeal_back.shop;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.BaseTestDataFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ShopGetIntegrationTest {
  
  @Autowired
  private MockMvc mockMvc;

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
  public void setUp() {
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
  class 取得できる場合 {
    @Test
    public void baseIdを指定して関連する店舗一覧を取得できる() throws Exception {
      mockMvc.perform(get("/api/shops")
        .param("baseId", base1.getId().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$.[0].name").value(shop1WithBase1.getName()));
    }
  }

  @Nested
  class 取得できない場合 {
    @Test
    public void 存在しないbaseIdを指定すると空のリストが返る() throws Exception {
      mockMvc.perform(get("/api/shops")
        .param("baseId", "9999"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shops", hasSize(0)));
    }
  }
}

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
import com.imeal.imeal_back.helper.testData.ReviewTestDataFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.review.entity.Review;
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
  @Autowired
  private ReviewTestDataFactory reviewTestDataFactory;

  private Base base1;
  private Base base2;

  private Shop shop1WithBase1;
  private Shop shop2WithBase1;
  private Shop shopWithBase2;

  private Review review1WithShop1;
  private Review review2WithShop1;

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

    //review作成
    review1WithShop1 = reviewTestDataFactory.builder().withShop(shop1WithBase1).buildAndPersist();
    review2WithShop1 = reviewTestDataFactory.builder().withShop(shop1WithBase1).buildAndPersist();
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

    @Test
    public void shopIdを指定して店舗に紐づくレビュー一覧を取得できる() throws Exception {
      mockMvc.perform(get("/api/shops/" + shop1WithBase1.getId() + "/reviews"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.reviews.[*]", hasSize(2)))
          .andExpect(jsonPath("$.reviews.[0].review.id").value(review1WithShop1.getId()));
    }

    @Test
    public void 飲食店情報が単体で取得できる() throws Exception {
      mockMvc.perform(get("/api/shops/" + shop1WithBase1.getId()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").exists()) 
          .andExpect(jsonPath("$.id").value(shop1WithBase1.getId()));
    }
  }

  @Nested
  class 取得できない場合 {
    @Test
    void 存在しないbaseIdを指定すると404エラーが返る() throws Exception {
      mockMvc.perform(get("/api/shops")
          .param("baseId", "9999")) // 存在しないID
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.messages").value("Shop not found with id: 9999")); 
    }

    @Test
    void 存在しないshopIdを指定すると404エラーが返る() throws Exception {
      mockMvc.perform(get("/api/shops/9999")) //存在しないID
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.messages").value("Shop not found with id: 9999"));
    }

    @Test
    void 存在しないshopIdを指定してレビュー情報を取得しようとすると404エラーが返る() throws Exception {
      mockMvc.perform(get("/api/shops/9999/reviews")) //存在しないID
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.messages").value("Shop not found with id: 9999"));
    }
  }
}

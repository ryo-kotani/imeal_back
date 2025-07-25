package com.imeal.imeal_back.review;

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
import com.imeal.imeal_back.helper.testData.BaseTestDataFactory;
import com.imeal.imeal_back.helper.testData.ReviewTestDataFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.shop.entity.Shop;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ReviewGetIntegrationTest {
  
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ReviewTestDataFactory reviewTestDataFactory;
  @Autowired
  BaseTestDataFactory baseTestDataFactory;
  @Autowired
  ShopTestDataFactory shopTestDataFactory;

  private Integer baseId;
  private Review existReview1;
  private Review existReview2;

  @BeforeEach
  public void setUp() {
    // baseを作成・テストに使用
    Base base = baseTestDataFactory.createDefaultBase();
    baseId = base.getId();
    Base otherBase = baseTestDataFactory.createDefaultBase();
    // shopを作成
    Shop shop = shopTestDataFactory.builder().withBase(base).buildAndPersist();
    Shop otherShop = shopTestDataFactory.builder().withBase(otherBase).buildAndPersist();

    // 作成したbaseを用いてreviewを3件作成
    reviewTestDataFactory.builder().withShop(otherShop).buildAndPersist();
    existReview1 = reviewTestDataFactory.builder().withShop(shop).buildAndPersist();
    existReview2 = reviewTestDataFactory.builder().withShop(shop).buildAndPersist();
  }

  @Nested
  class リスト取得できる場合 {
    @Test
    public void 正しいリクエストで取得できる() throws Exception{
      mockMvc.perform(get("/api/reviews?baseId=" + baseId + "&sort=-createdAt&limit=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*]", hasSize(2)))
        .andExpect(jsonPath("$.[0].comment").value(existReview2.getComment()))
        .andExpect(jsonPath("$.[1].comment").value(existReview1.getComment()));
    }
  }

  @Nested
  class リスト取得できない場合 {

  }

  @Nested
  class 単体取得できる場合 {

  }

  @Nested
  class 単体取得できない場合 {

  }
}

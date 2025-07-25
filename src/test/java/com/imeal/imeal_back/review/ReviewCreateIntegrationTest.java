package com.imeal.imeal_back.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.helper.lib.TestAuthHelper;
import com.imeal.imeal_back.helper.request.ReviewCreateRequestFactory;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.ShopTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.repository.ReviewRepository;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ReviewCreateIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;

  @Autowired
  private ShopTestDataFactory shopTestDataFactory;

  @Autowired
  private UserTestDataFactory userTestDataFactory;

  @Autowired
  private ReviewCreateRequestFactory reviewCreateRequestFactory;

  @Autowired
  private TestAuthHelper testAuthHelper;

  @Autowired
  private ReviewRepository reviewRepository;

  private ReviewCreateRequest request;
  private UserCreateRequest userCreateRequest;

  private Integer beforeCount;
  private Integer afterCount;
  
  @BeforeEach
  public void setUp() {
    userCreateRequest = userCreateRequestFactory.createValidRequest();
    userTestDataFactory.builder().withEmail(userCreateRequest.getEmail()).withPassword(userCreateRequest.getPassword()).buildAndPersist();
    
    Shop shop = shopTestDataFactory.createDefaultShop();

    request = reviewCreateRequestFactory.builder().withShopId(shop.getId()).build();
    beforeCount = reviewRepository.count();
  }

  @Nested
  public class review作成ができる場合{
    @Test
    public void 正しいリクエストだと作成できる() throws Exception {
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(userCreateRequest.getEmail(), userCreateRequest.getPassword());

      mockMvc.perform(post("/api/reviews").session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .with(csrf()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.comment").value(request.getComment()));

      afterCount = reviewRepository.count();
      assertEquals(beforeCount + 1, afterCount);
    }
  }

  @Nested
  public class review作成ができない場合{
    @Test
    public void ログインしていないと作成できない() throws Exception {

      mockMvc.perform(post("/api/reviews")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .with(csrf()))
        .andExpect(status().is3xxRedirection());

      afterCount = reviewRepository.count();
      assertEquals(beforeCount, afterCount);
    }

    @Test
    public void 不正なリクエストだと作成できない() throws Exception {
      request.setImgPath("");
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(userCreateRequest.getEmail(), userCreateRequest.getPassword());

      mockMvc.perform(post("/api/reviews").session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.messages").exists());

      afterCount = reviewRepository.count();
      assertEquals(beforeCount, afterCount);
    }
  }
}

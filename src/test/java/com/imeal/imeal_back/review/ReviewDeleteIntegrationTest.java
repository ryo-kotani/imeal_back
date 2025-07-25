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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.helper.lib.TestAuthHelper;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.ReviewTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ReviewDeleteIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ReviewTestDataFactory reviewTestDataFactory;

  @Autowired
  private TestAuthHelper testAuthHelper;

  @Autowired
  private UserTestDataFactory userTestDataFactory;

  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;

  @Autowired
  private ReviewRepository reviewRepository;

  private Review existReview;
  private String loginEmail;
  private String loginPassword;
  private Integer beforeCount;
  private Integer afterCount;
  
  @BeforeEach
  public void setUp() {
    // review作成
    existReview = reviewTestDataFactory.createDefaultReview();

    // user作成
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();
    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();
    userTestDataFactory.builder().withEmail(loginEmail).withPassword(loginPassword).buildAndPersist();
  }

  @Nested
  public class review削除ができる場合{
    @Test
    public void ログインしていると削除できる() throws Exception {
      // ログイン・セッション情報取得
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);
      beforeCount = reviewRepository.count();

      // 削除処理
      mockMvc.perform(delete("/api/reviews/" + existReview.getId()).session(session)
        .with(csrf()))
        .andExpect(status().isNoContent());

      // カウント確認
      afterCount = reviewRepository.count();
      assertEquals(beforeCount - 1, afterCount);
      // レコード確認
      mockMvc.perform(get("/api/reviews/" + existReview.getId()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.messages").exists());
    }
  }

  @Nested
  public class review削除ができない場合{
    @Test
    public void ログインしていないと削除できない() throws Exception {
      // 準備
      beforeCount = reviewRepository.count();

      // 実行
      mockMvc.perform(delete("/api/reviews/" + existReview.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .with(csrf()))
        .andExpect(status().is3xxRedirection());

      // カウント確認
      afterCount = reviewRepository.count();
      assertEquals(beforeCount, afterCount);
      // レコード確認
      mockMvc.perform(get("/api/reviews/" + existReview.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.comment").value(existReview.getComment()));
    }

    @Test
    public void テーブルに存在しないと削除できない() throws Exception {
      // 準備
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);
      Integer notExistId = 999;
      beforeCount = reviewRepository.count();

      // 削除処理
      mockMvc.perform(delete("/api/reviews/" + notExistId).session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .with(csrf()))
        .andExpect(status().isNotFound());

      // カウント確認
      afterCount = reviewRepository.count();
      assertEquals(beforeCount, afterCount);
      // レコード確認
      mockMvc.perform(get("/api/reviews/" + existReview.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.comment").value(existReview.getComment()));
    }
  }
}

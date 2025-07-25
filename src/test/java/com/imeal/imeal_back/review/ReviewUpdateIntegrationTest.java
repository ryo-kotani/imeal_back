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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.helper.lib.TestAuthHelper;
import com.imeal.imeal_back.helper.request.ReviewUpdateRequestFactory;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.ReviewTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ReviewUpdateIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReviewTestDataFactory reviewTestDataFactory;

  @Autowired
  private ReviewUpdateRequestFactory reviewUpdateRequestFactory;

  @Autowired
  private TestAuthHelper testAuthHelper;

  @Autowired
  private UserTestDataFactory userTestDataFactory;

  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;

  @Autowired
  private ReviewRepository reviewRepository;

  private ReviewUpdateRequest updateRequest;
  private Review existReview;
  private String loginEmail;
  private String loginPassword;
  
  @BeforeEach
  public void setUp() {
    // review作成
    updateRequest = reviewUpdateRequestFactory.createValidRequest();
    existReview = reviewTestDataFactory.createDefaultReview();

    // user作成
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();
    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();
    userTestDataFactory.builder().withEmail(loginEmail).withPassword(loginPassword).buildAndPersist();
  }

  @Nested
  public class review更新ができる場合{
    @Test
    public void 正しいリクエストだと更新できる() throws Exception {
      // ログイン処理
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);

      // updateメソッドを実行・確認
      mockMvc.perform(put("/api/reviews/" + existReview.getId()).session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest))
        .with(csrf()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.comment").value(updateRequest.getComment()));

      // テーブルのreviewを確認
      Review updatedReview = reviewRepository.findById(existReview.getId()).orElseThrow();
      assertEquals(updateRequest.getComment(), updatedReview.getComment());
    }
  }

  @Nested
  public class review更新ができない場合{
    @Test
    public void ログインしていないと更新できない() throws Exception {
      // updateメソッドを実行・確認
      mockMvc.perform(put("/api/reviews/" + existReview.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest))
        .with(csrf()))
        .andExpect(status().is3xxRedirection());

      // テーブルのreviewを確認
      Review updatedReview = reviewRepository.findById(existReview.getId()).orElseThrow();
      assertEquals(existReview.getComment(), updatedReview.getComment());
    }

    @Test
    public void 不正なリクエストだと更新できない() throws Exception {
      // 不正なリクエスト作成・ログイン
      updateRequest = reviewUpdateRequestFactory.builder().withImgPath("").build();
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);

      // update処理を実行・確認
      mockMvc.perform(put("/api/reviews/" + existReview.getId()).session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest))
        .with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.messages").exists());

      // テーブルのreviewを確認
      Review updatedReview = reviewRepository.findById(existReview.getId()).orElseThrow();
      assertEquals(existReview.getComment(), updatedReview.getComment());
    }

    @Test
    public void 存在しない口コミは更新できない() throws Exception {
      // 準備
      Integer notExistReviewId = 9999;
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);

      // 実行
      mockMvc.perform(put("/api/reviews/" + notExistReviewId).session(session)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest))
        .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.messages").exists());
    }
  }
}

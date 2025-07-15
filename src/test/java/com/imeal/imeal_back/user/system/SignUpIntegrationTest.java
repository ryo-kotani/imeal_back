package com.imeal.imeal_back.user.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.factory.UserCreateRequestFactory;
import com.imeal.imeal_back.user.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = ImealBackApplication.class)
@AutoConfigureMockMvc
public class SignUpIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper; // JSON変換用

  private UserCreateRequest request;

  @BeforeEach
  public void setUp() {
    // 各テストの前に有効なリクエストを準備
    request = UserCreateRequestFactory.createValidRequest();
  }

  // @AfterEach
  // public void cleanUp() {
  //   userRepository.deleteAll();
  // }

  @Nested
  class ユーザー新規登録ができる場合 {
    @Test
    void 適切なリクエストがあればユーザー新規登録ができレスポンスが返る() throws Exception {
      // 1. 準備 (Arrange)
      long initialUserCount = userRepository.count();

      // 2. 実行 (Act) & 3. 検証 (Assert) - レスポンスの検証
      mockMvc.perform(post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated()) // HTTP 201 Created
        .andExpect(jsonPath("$.id").exists()) // idが返却される
        .andExpect(jsonPath("$.name").value(request.getName())); // nameが一致する

      // 3. 検証 (Assert) - DBの状態変化を検証
      long afterUserCount = userRepository.count();
      assertEquals(initialUserCount + 1, afterUserCount, "ユーザーが1人増えていること");
    }
  }

  @Nested
  class ユーザー新規登録ができない場合 {
    @Test
    void 不適切なリクエストではユーザー新規登録ができずエラーが返る() throws Exception {
      // 1. 準備 (Arrange)
      // nameが空の不正なリクエストを作成
      request.setName("");
      long initialUserCount = userRepository.count(); // 実行前のユーザー数を取得

      // 2. 実行 (Act) & 3. 検証 (Assert) - レスポンスの検証
      mockMvc.perform(post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest()); // HTTP 400 Bad Request

      // 3. 検証 (Assert) - DBの状態変化を検証
      long afterUserCount = userRepository.count();
      assertEquals(initialUserCount, afterUserCount, "ユーザー数が増えていないこと");
    }
  }
}
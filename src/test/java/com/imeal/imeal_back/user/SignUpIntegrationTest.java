package com.imeal.imeal_back.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
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
  private UserCreateRequestFactory userCreateRequestFactory;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  class ユーザー新規登録ができる場合 {
    @Test
    void 適切なリクエストがあればユーザー新規登録ができレスポンスが返る() throws Exception {
      UserCreateRequest request = userCreateRequestFactory.createValidRequest();
      long initialUserCount = userRepository.count();

      mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(request.getName()));

      long afterUserCount = userRepository.count();
      assertEquals(initialUserCount + 1, afterUserCount, "ユーザーが1人増えていること");
    }
  }

  @Nested
  class ユーザー新規登録ができない場合 {
    @Test
    void 不適切なリクエストではユーザー新規登録ができずエラーが返る() throws Exception {
      UserCreateRequest badRequest = userCreateRequestFactory.builder().withEmail("").build();
      long initialUserCount = userRepository.count();

      mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(badRequest)))
        .andExpect(status().isBadRequest());

      long afterUserCount = userRepository.count();
      assertEquals(initialUserCount, afterUserCount, "ユーザー数が増えていないこと");
    }
  }
}
package com.imeal.imeal_back.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class LoginIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;

  @Autowired
  private UserTestDataFactory userTestDataFactory;

  private String loginEmail;
  private String loginPassword;

  private User existUser;

  @BeforeEach
  public void setUp() {
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();

    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();

    userTestDataFactory.builder()
      .withEmail(loginEmail)
      .withPassword(loginPassword)
      .buildAndPersist();

    existUser = userRepository.findByEmail(loginEmail);
  }

  @Nested
  class ログインができる場合 {
    @Test
    public void 保存されているユーザー情報と合致すればログインできる() throws Exception {
      mockMvc.perform(post("/api/login")
        .param("email", loginEmail)
        .param("password", loginPassword)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(existUser.getName()))
        .andExpect(jsonPath("$.id").value(existUser.getId()));
    }
  }

  @Nested
  class ログインできない場合 {
    @Test
    public void 保存されているユーザー情報と合致しなければログインできない() throws Exception {
      mockMvc.perform(post("/api/login")
        .param("email", loginEmail)
        .param("password", "wrongPassword")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .with(csrf()))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.messages").exists());
    }
  }
}

package com.imeal.imeal_back.user.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.factory.UserCreateRequestFactory;
import com.imeal.imeal_back.user.repository.UserRepository;
import com.imeal.imeal_back.user.service.UserService;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class LoginIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  private UserCreateRequest request;

  private User existUser;

  @BeforeEach
  public void setUp() {
    request = UserCreateRequestFactory.createValidRequest();
    userService.createUser(request);
    existUser = userRepository.findByEmail(request.getEmail());
  }

  @Nested
  class ログインができる場合 {
    @Test
    public void 保存されているユーザー情報と合致すればログインできる() throws Exception {
      mockMvc.perform(post("/api/login")
        .param("email", request.getEmail())
        .param("password", request.getPassword())
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
        .param("email", request.getEmail())
        .param("password", "wrongPassword")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .with(csrf()))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("errorMessages").exists());
    }
  }
}

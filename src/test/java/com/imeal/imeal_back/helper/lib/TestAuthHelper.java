package com.imeal.imeal_back.helper.lib;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestAuthHelper {

  private final MockMvc mockMvc;

  public MockHttpSession performLoginAndGetSession(String email, String password) throws Exception {

    MvcResult loginResult = mockMvc.perform(post("/api/login")
      .param("email", email)
      .param("password", password)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .with(csrf()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").exists())
      .andExpect(jsonPath("$.id").exists())
      .andReturn();
    
    return (MockHttpSession) loginResult.getRequest().getSession();
  }
}

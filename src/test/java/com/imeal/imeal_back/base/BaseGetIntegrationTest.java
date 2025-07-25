package com.imeal.imeal_back.base;

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

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class BaseGetIntegrationTest {
  
  @Autowired
  MockMvc mockMvc;

  @Autowired
  BaseTestDataFactory baseTestDataFactory;

  private Base existBase;

  @BeforeEach
  public void setUp() {
    existBase = baseTestDataFactory.createDefaultBase();
    baseTestDataFactory.createDefaultBase();
    baseTestDataFactory.createDefaultBase();
  }

  @Nested
  class 拠点取得ができる場合 {
    @Test
    public void 正しいリクエストで取得できる() throws Exception {
      mockMvc.perform(get("/api/bases"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].name").value(existBase.getName()));
    }
  }
}

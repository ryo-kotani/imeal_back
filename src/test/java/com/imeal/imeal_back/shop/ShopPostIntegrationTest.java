package com.imeal.imeal_back.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.helper.lib.TestAuthHelper;
import com.imeal.imeal_back.helper.request.ShopCreateRequestFactory;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.helper.testData.BaseTestDataFactory;
import com.imeal.imeal_back.helper.testData.LocationTestDataFactory;
import com.imeal.imeal_back.helper.testData.UserTestDataFactory;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.repository.ShopRepository;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ShopPostIntegrationTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ShopCreateRequestFactory shopCreateRequestFactory;

  @Autowired
  private BaseTestDataFactory baseTestDataFactory;
  @Autowired
  private LocationTestDataFactory locationTestDataFactory;

  @Autowired
  private UserCreateRequestFactory userCreateRequestFactory;
  @Autowired
  private UserTestDataFactory userTestDataFactory;
  @Autowired
  private TestAuthHelper testAuthHelper;

  @Autowired
  private ShopRepository shopRepository;

  private String loginEmail;
  private String loginPassword;

  private Integer baseId;
  private Integer locationId;

  @BeforeEach
  public void setUp() {
    // base作成
    Base base = baseTestDataFactory.createDefaultBase();
    baseId = base.getId();

    // location作成
    Location location = locationTestDataFactory.createDefaultLocation();
    locationId = location.getId();

    // user作成
    UserCreateRequest userCreateRequest = userCreateRequestFactory.createValidRequest();
    loginEmail = userCreateRequest.getEmail();
    loginPassword = userCreateRequest.getPassword();
    userTestDataFactory.builder().withEmail(loginEmail).withPassword(loginPassword).buildAndPersist();
  }

  @Nested
  class 作成に成功する場合 {
    @Test
    public void 正常なリクエストで店舗を作成できる() throws Exception {
      // 1. 準備 (Arrange)
      long countBefore = shopRepository.count();
      MockHttpSession session = testAuthHelper.performLoginAndGetSession(loginEmail, loginPassword);
      ShopCreateRequest request = shopCreateRequestFactory.createValidRequest();

      // 2. 実行 (Act) & 3. 検証 (Assert)
      mockMvc.perform(post("/api/shops").session(session)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.shop.id").exists())
          .andExpect(jsonPath("$.shop.name").value(request.getName()))
          .andExpect(jsonPath("$.shop.location.lat").value(request.getLocationLat()));

      // DBの状態変化を確認
      assertEquals(countBefore + 1, shopRepository.count());
    }
  }

  @Nested
  class 作成に失敗する場合 {

  }
}

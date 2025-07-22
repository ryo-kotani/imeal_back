package com.imeal.imeal_back.review;

import java.math.BigDecimal;

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
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.helper.request.ReviewCreateRequestFactory;
import com.imeal.imeal_back.helper.request.UserCreateRequestFactory;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.service.ReviewService;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.service.ShopService;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.service.UserService;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
public class ReviewDeleteIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;
  @Autowired
  private LocationRepository locationRepository;
  @Autowired
  private BaseRepository baseRepository;
  @Autowired
  private ShopService shopService;
  @Autowired
  private ReviewService reviewService;


  private ReviewCreateRequest createRequest;
  private UserCreateRequest userRequest;
  private Location location;
  private Base base;
  private ShopCreateRequest shopRequest;

  // response
  private ReviewShopUserResponse reviewCreateResponse;
  
  @BeforeEach
  public void setUp() {
    
    // user作成
    userRequest = UserCreateRequestFactory.createValidRequest();
    UserResponse userResponse = userService.createUser(userRequest);

    // location作成
    location = new Location();
    location.setLat(new BigDecimal("11.111111"));
    location.setLon(new BigDecimal("111.111111"));
    locationRepository.insert(location);

    // base作成
    base = new Base();
    base.setName("fhaose");
    base.setLocation(location);
    baseRepository.insert(base);

    // shop作成
    shopRequest = new ShopCreateRequest();
    shopRequest.setUrl("jfoaewh");
    shopRequest.setName("hfoae");
    shopRequest.setAddress("jfaoe");
    shopRequest.setDistance(3829);
    shopRequest.setMinutes(483);
    shopRequest.setBaseId(base.getId());
    shopRequest.setLocationLat(new BigDecimal("22.222222"));
    shopRequest.setLocationLon(new BigDecimal("222.222222"));
    ShopResponse shopResponse = shopService.createShop(shopRequest);

    // reviewのpostリクエスト作成
    createRequest = ReviewCreateRequestFactory.builder().withShopId(shopResponse.getShop().getId()).build();
    reviewCreateResponse = reviewService.createReview(userResponse.getId(), createRequest);
  }

  @Nested
  public class review削除ができる場合{
    @Test
    public void ログインしていると削除できる() throws Exception {
      // ログイン処理
      MvcResult loginResult = mockMvc.perform(post("/api/login")
        .param("email", userRequest.getEmail())
        .param("password", userRequest.getPassword())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(userRequest.getName()))
        .andReturn();
      MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession();

      // 削除処理
      mockMvc.perform(delete("/api/reviews/" + reviewCreateResponse.getReview().getId()).session(session)
        .with(csrf()))
        .andExpect(status().isCreated());
    }
  }

  @Nested
  public class review削除ができない場合{
    @Test
    public void ログインしていないと削除できない() throws Exception {
      mockMvc.perform(delete("/api/reviews/" + reviewCreateResponse.getReview().getId())
        .contentType(MediaType.APPLICATION_JSON)
        .with(csrf()))
        .andExpect(status().is3xxRedirection());
    }
  }
}

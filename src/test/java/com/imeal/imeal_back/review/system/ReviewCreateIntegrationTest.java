package com.imeal.imeal_back.review.system;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.imeal.imeal_back.ImealBackApplication;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.factory.ReviewCreateRequestFactory;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.factory.UserCreateRequestFactory;
import com.imeal.imeal_back.user.service.UserService;

import lombok.RequiredArgsConstructor;

@ActiveProfiles("test")
@SpringBootTest(classes=ImealBackApplication.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class ReviewCreateIntegrationTest {

  private final MockMvc mockMvc;
  private final UserService userService;
  private final LocationRepository locationRepository;

  @BeforeEach
  public void setUp() {
    ReviewCreateRequest request = ReviewCreateRequestFactory.createValidRequest();
    UserCreateRequest userRequest = UserCreateRequestFactory.createValidRequest();
    userService.createUser(userRequest);
    Location location = new Location();
    location.setId(1);
    location.setLat(new BigDecimal("11.111111"));
    location.setLon(new BigDecimal("111.111111"));
    locationRepository.insert(location);
    
  }
}

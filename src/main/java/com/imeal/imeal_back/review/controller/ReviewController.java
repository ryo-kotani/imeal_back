package com.imeal.imeal_back.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.common.validation.ValidationGroups;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopResponse;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.service.ReviewService;
import com.imeal.imeal_back.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  /**
   * 条件に合ったreviewのリストをレスポンスする
   * 存在しない場合は404を返す
   * @param baseId
   * @param sort
   * @param limit
   * @return review, shop, userのリスト
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ReviewShopUserResponse> getReviews(
    @RequestParam("baseId") Integer baseId,
    @RequestParam(name = "sort", required = false) String sort,
    @RequestParam(name = "limit", required = false) Integer limit
  ) {
    return reviewService.getReviews(baseId, sort, limit);
  }

  /**
   * reviewを単一で取得する
   * 存在しない場合は404を返す
   * @param reviewId
   * @return reviewIdに紐づいたreview
   */
  @GetMapping("/{reviewId}")
  @ResponseStatus(HttpStatus.OK)
  public ReviewShopResponse getReview(@PathVariable("reviewId") Integer reviewId) {
    return reviewService.getReview(reviewId);
  }

  /**
   * review情報はrequestから、user情報はcurrentUserから取得し、insertする
   * @param request
   * @param currentUser
   * @return review, shop, userを返す
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewShopUserResponse postReview(
    @RequestBody @Validated(ValidationGroups.Group.class) ReviewCreateRequest request,
    @AuthenticationPrincipal CustomUserDetails currentUser
  ) {
    return reviewService.createReview(currentUser.getUser().getId(), request);
  }

  @PutMapping("/{reviewId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewShopUserResponse putReview(
    @PathVariable("reviewId") Integer reviewId,
    @RequestBody @Validated(ValidationGroups.Group.class) ReviewUpdateRequest request
  ) {
    return reviewService.updateReview(reviewId, request);
  }

  @DeleteMapping("/{reviewId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReview(@PathVariable("reviewId") Integer reviewId) {
    reviewService.deleteReview(reviewId);
  }
}

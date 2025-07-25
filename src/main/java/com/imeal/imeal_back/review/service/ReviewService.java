package com.imeal.imeal_back.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imeal.imeal_back.common.exception.ResourceNotFoundException;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopResponse;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  /**
   * baseId, sort, limitの条件に応じて口コミを取得する
   * アノテーションを付与してトランザクション処理にした
   * リソースが存在しない場合、例外をスローする
   * @param baseId
   * @param sort
   * @param limit
   * @return reviewのリスト, reviewに紐づくuser, reviewに紐づくshop, shopに紐づくlocation
   */
  @Transactional(readOnly=true)
  public List<ReviewShopUserResponse> getReviews(Integer baseId, String sort, Integer limit) {
    List<Review> reviews = reviewRepository.findByX(baseId, sort, limit);
    // リソースが存在しない場合は例外をスローする
    if (reviews.isEmpty()) {
      throw new ResourceNotFoundException("次の条件を満たすリソースが存在しません: 拠点ID: " + baseId);
    }
    List<ReviewShopUserResponse> response = reviewMapper.toReviewsShopUserResponse(reviews);
    return response;
  }

  //レビュー情報(単体) + 店舗情報を取得
  // @Transactionalを付与し、トランザクション処理にした
  // orElseThrowでリソースが存在しない場合例外をスローするようにした
  @Transactional(readOnly=true)
  public ReviewShopResponse getReview(Integer reviewId){
    Review reviewWithShop = reviewRepository.findWithShopLocationById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException("次の条件を満たすリソースが存在しません: 口コミID: " + reviewId));
    return reviewMapper.toReviewShopResponse(reviewWithShop);
  }

  // @Transactionalを付与し、トランザクション処理にした
  @Transactional
  public ReviewShopUserResponse createReview(Integer userId, ReviewCreateRequest request) {
    Review review = reviewMapper.toModel(request, userId);
    reviewRepository.insert(review);
    Review createdReview = reviewRepository.findWithShopLocationUserById(review.getId());
    return reviewMapper.toReviewShopUserResponse(createdReview);
  }

  // @Transactionalを付与し、トランザクション処理にした
  @Transactional
  public ReviewShopUserResponse updateReview(Integer reviewId, ReviewUpdateRequest request) {
    // 存在しない場合は例外をスローする
    Review review = reviewRepository.findById(reviewId)
      .orElseThrow(() -> new ResourceNotFoundException("次の条件を満たすリソースが存在しません: 口コミID: " + reviewId));

    Review reviewToUpdate = reviewMapper.toModelFromUpdate(request, reviewId);
    reviewRepository.update(reviewToUpdate);
    Review updatedReview = reviewRepository.findWithShopLocationUserById(reviewId);
    return reviewMapper.toReviewShopUserResponse(updatedReview);
  }

  public void deleteReview(Integer reviewId) {
    // deleteメソッドの戻り値（削除件数）で存在を判断する
    int affectedRows = reviewRepository.delete(reviewId);
    if (affectedRows == 0) {
      // 1件も削除されなかった場合はリソースが存在しない
      throw new ResourceNotFoundException("Review not found with id: " + reviewId);
    }
    
  }
}

package com.imeal.imeal_back.helper.request;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.helper.faker.ReviewFaker;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCreateRequestFactory {
  private final ReviewFaker reviewFaker;

  // デフォルトのリクエストオブジェクトを生成
  public ReviewCreateRequest createValidRequest() {
    return builder().build();
  }

  // ビルダーのエントリーポイント
  public ReviewCreateRequestBuilder builder() {
    return new ReviewCreateRequestBuilder();
  }

  // ランダムなリクエストオブジェクトを生成
  public class ReviewCreateRequestBuilder {
    private String imgPath = reviewFaker.createImgPath();
    private String comment = reviewFaker.createComment();
    private Integer amount = reviewFaker.createAmount();
    private Integer evaluation = reviewFaker.createEvaluation();
    private Integer shopId = 1;

    public ReviewCreateRequestBuilder withImgPath(String imgPath) {
      this.imgPath = imgPath;
      return this;
    }

    public ReviewCreateRequestBuilder withComment(String comment) {
      this.comment = comment;
      return this;
    }

    public ReviewCreateRequestBuilder withAmount(Integer amount) {
      this.amount = amount;
      return this;
    }

    public ReviewCreateRequestBuilder withEvaluation(Integer evaluation) {
      this.evaluation = evaluation;
      return this;
    }

    public ReviewCreateRequestBuilder withShopId(Integer shopId) {
      this.shopId = shopId;
      return this;
    }

    public ReviewCreateRequest build() {
      ReviewCreateRequest request = new ReviewCreateRequest();
      request.setImgPath(this.imgPath);
      request.setComment(this.comment);
      request.setAmount(this.amount);
      request.setEvaluation(this.evaluation);
      request.setShopId(this.shopId);
      return request;
    }
  }
}

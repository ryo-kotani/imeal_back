package com.imeal.imeal_back.review.factory;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;

public class ReviewCreateRequestFactory {
  private static final Faker FAKER = new Faker();

  // デフォルトのリクエストオブジェクトを生成
  public static ReviewCreateRequest createValidRequest() {
    return builder().build();
  }

  // ビルダーのエントリーポイント
  public static ReviewCreateRequestBuilder builder() {
    return new ReviewCreateRequestBuilder();
  }

  // ランダムなリクエストオブジェクトを生成
  public static class ReviewCreateRequestBuilder {
    private String imgPath = FAKER.lorem().characters(1, 256);
    private String comment = FAKER.lorem().characters(1, 256);
    private Integer amount = FAKER.number().hashCode();
    private Integer evaluation = FAKER.number().numberBetween(0, 5);
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

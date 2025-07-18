package com.imeal.imeal_back.review.factory;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;

public class ReviewUpdateRequestFactory {
  private static final Faker FAKER = new Faker();

  // デフォルトのリクエストオブジェクトを生成
  public static ReviewUpdateRequest createValidRequest() {
    return builder().build();
  }

  // ビルダーのエントリーポイント
  public static ReviewUpdateRequestBuilder builder() {
    return new ReviewUpdateRequestBuilder();
  }

  // ランダムなリクエストオブジェクトを生成
  public static class ReviewUpdateRequestBuilder {
    private String imgPath = FAKER.lorem().characters(1, 256);
    private String comment = FAKER.lorem().characters(1, 256);
    private Integer amount = FAKER.number().hashCode();
    private Integer evaluation = FAKER.number().numberBetween(0, 5);

    public ReviewUpdateRequestBuilder withImgPath(String imgPath) {
      this.imgPath = imgPath;
      return this;
    }

    public ReviewUpdateRequestBuilder withComment(String comment) {
      this.comment = comment;
      return this;
    }

    public ReviewUpdateRequestBuilder withAmount(Integer amount) {
      this.amount = amount;
      return this;
    }

    public ReviewUpdateRequestBuilder withEvaluation(Integer evaluation) {
      this.evaluation = evaluation;
      return this;
    }

    public ReviewUpdateRequest build() {
      ReviewUpdateRequest request = new ReviewUpdateRequest();
      request.setImgPath(this.imgPath);
      request.setComment(this.comment);
      request.setAmount(this.amount);
      request.setEvaluation(this.evaluation);
      return request;
    }
  }
}

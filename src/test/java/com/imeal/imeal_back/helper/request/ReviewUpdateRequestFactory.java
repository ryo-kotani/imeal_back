package com.imeal.imeal_back.helper.request;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.helper.faker.ReviewFaker;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewUpdateRequestFactory {
  private final ReviewFaker reviewFaker;

  // デフォルトのリクエストオブジェクトを生成
  public ReviewUpdateRequest createValidRequest() {
    return builder().build();
  }

  // ビルダーのエントリーポイント
  public ReviewUpdateRequestBuilder builder() {
    return new ReviewUpdateRequestBuilder();
  }

  // ランダムなリクエストオブジェクトを生成
  public class ReviewUpdateRequestBuilder {
    private String imgPath = reviewFaker.createImgPath();
    private String comment = reviewFaker.createComment();
    private Integer amount = reviewFaker.createAmount();
    private Integer evaluation = reviewFaker.createEvaluation();

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

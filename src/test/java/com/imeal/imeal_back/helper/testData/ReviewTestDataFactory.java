package com.imeal.imeal_back.helper.testData;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.ReviewFaker;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewTestDataFactory {
  
  // 基本機能
  private final ReviewFaker reviewFaker;
  private final ReviewRepository reviewRepository;

  // 外部キー制約のあるテストデータを生成
  private final ShopTestDataFactory shopTestDataFactory;
  private final UserTestDataFactory userTestDataFactory;

  public Review createDefaultReview() {
    return builder().buildAndPersist();
  }
  public ReviewTestDataBuilder builder() {
    return new ReviewTestDataBuilder();
  }

  public class ReviewTestDataBuilder {
    private String imgPath = reviewFaker.createImgPath();
    private String comment = reviewFaker.createComment();
    private Integer amount = reviewFaker.createAmount();
    private Integer evaluation = reviewFaker.createEvaluation();
    private Shop shop;
    private User user;

    public ReviewTestDataBuilder withImgPath(String imgPath) {
      this.imgPath = imgPath;
      return this;
    }

    public ReviewTestDataBuilder withComment(String comment) {
      this.comment = comment;
      return this;
    }

    public ReviewTestDataBuilder withAmount(Integer amount) {
      this.amount = amount;
      return this;
    }

    public ReviewTestDataBuilder withEvaluation(Integer evaluation) {
      this.evaluation = evaluation;
      return this;
    }

    public ReviewTestDataBuilder withShop(Shop shop) {
      this.shop = shop;
      return this;
    }

    public ReviewTestDataBuilder withUser(User user) {
      this.user = user;
      return this;
    }

    public Review build() {
      if (shop == null) {
        shop = shopTestDataFactory.createDefaultShop();
      }

      if (user == null) {
        user = userTestDataFactory.createDefaultUser();
      }

      Review review = new Review();

      review.setImgPath(imgPath);
      review.setComment(comment);
      review.setAmount(amount);
      review.setEvaluation(evaluation);
      review.setShop(shop);
      review.setUser(user);

      return review;
    }

    public Review buildAndPersist() {
      Review reviewToPersist = build();
      reviewRepository.insert(reviewToPersist);
      return reviewToPersist;
    }
  }
}

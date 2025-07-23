package com.imeal.imeal_back.review.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
  private Integer id;
  private String imgPath;
  private String comment;
  private Integer amount;
  private Integer evaluation;
  private Timestamp createdAt;
}

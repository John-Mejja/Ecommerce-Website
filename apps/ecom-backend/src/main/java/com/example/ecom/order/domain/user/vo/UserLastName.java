package com.example.ecom.order.domain.user.vo;

import com.example.ecom.shared.error.domain.Assert;

public record UserLastName(String value) {

  public UserLastName {
    Assert.field("value", value).maxLength(255);
  }
}

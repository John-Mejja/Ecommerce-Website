package com.example.ecom.shared.jpa;

import com.example.ecom.shared.error.domain.Assert;

public record UserAddress(String street, String city, String zipCode, String country) {

  public UserAddress{
    Assert.field(street,"street").notNull();
    Assert.field(city, "city").notNull();
    Assert.field(zipCode, "zipcode").notNull();
    Assert.field(country, "country").notNull();
  }
}

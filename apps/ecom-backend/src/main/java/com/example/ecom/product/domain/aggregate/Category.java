package com.example.ecom.product.domain.aggregate;

import com.example.ecom.product.domain.vo.CategoryName;
import com.example.ecom.product.domain.vo.PublicId;
import com.example.ecom.shared.error.domain.Assert;
import lombok.Getter;
import org.jilt.Builder;

import java.util.UUID;

@Builder
@Getter
public class Category {
  private final CategoryName name;
  private Long dbId;
  private PublicId publicId;

  public Category(CategoryName name, Long dbId, PublicId publicId) {
    assertMandatoryFields(name);
    this.name = name;
    this.publicId = publicId;
    this.dbId = dbId;
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  private void assertMandatoryFields(CategoryName categoryName) {
    Assert.notNull("name", categoryName);
  }
}

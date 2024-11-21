package com.example.ecom.product.domain.aggregate;

import com.example.ecom.product.domain.vo.*;
import com.example.ecom.shared.error.domain.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jilt.Builder;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class Product {

  private final ProductBrand productBrand;
  private final ProductColor color;
  private final ProductDescription description;
  private final ProductName name;
  private final ProductPrice price;
  private final ProductSize size;
  private final Category category;
  private final List<Picture> pictures;
  private Long dbId;
  private boolean featured;
  private PublicId publicId;
  private int nbInStock;

  public Product(Category category, ProductColor color, Long dbId, ProductDescription description, boolean featured, ProductName name, int nbInStock, List<Picture> pictures, ProductPrice price, ProductBrand productBrand, PublicId publicId, ProductSize size) {

    assertMandatoryFields(productBrand,color,description,name,price,size,category,pictures,false,nbInStock);
    this.category = category;
    this.color = color;
    this.dbId = dbId;
    this.description = description;
    this.featured = featured;
    this.name = name;
    this.nbInStock = nbInStock;
    this.pictures = pictures;
    this.price = price;
    this.productBrand = productBrand;
    this.publicId = publicId;
    this.size = size;
  }

  private void assertMandatoryFields(ProductBrand brand,
                                     ProductColor color,
                                     ProductDescription description,
                                     ProductName name,
                                     ProductPrice price,
                                     ProductSize size,
                                     Category category,
                                     List<Picture> pictures,
                                     boolean featured,
                                     int nbInStock) {
    Assert.notNull("brand", brand);
    Assert.notNull("color", color);
    Assert.notNull("description", description);
    Assert.notNull("name", name);
    Assert.notNull("price", price);
    Assert.notNull("size", size);
    Assert.notNull("category", category);
    Assert.notNull("pictures", pictures);
    Assert.notNull("featured", featured);
    Assert.notNull("nbInStock", nbInStock);
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  public boolean getFeatured() {
    return featured;
  }
}

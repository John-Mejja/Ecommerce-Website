package com.example.ecom.product.infrastructure.primary;

import com.example.ecom.product.domain.aggregate.Product;
import com.example.ecom.product.domain.aggregate.ProductBuilder;
import com.example.ecom.product.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestProduct {

  private String brand;
  private String color;
  private String description;
  private String name;
  private double price;
  private ProductSize size;
  private RestCategory category;
  private boolean featured;
  private List<RestPicture> pictures;
  private UUID publicId;
  private int nbInStock;

  public void addPictureAttachment(List<RestPicture> pictures) {
    this.pictures.addAll(pictures);
  }

  public static Product toDomain(RestProduct restProduct) {
    ProductBuilder productBuilder = ProductBuilder.product()
      .productBrand(new ProductBrand(restProduct.getBrand()))
      .color(new ProductColor(restProduct.getColor()))
      .description(new ProductDescription(restProduct.getDescription()))
      .name(new ProductName(restProduct.getName()))
      .price(new ProductPrice(restProduct.getPrice()))
      .size(restProduct.getSize())
      .category(RestCategory.toDomain(restProduct.getCategory()))
      .featured(restProduct.isFeatured())
      .nbInStock(restProduct.getNbInStock());

    if(restProduct.publicId != null) {
      productBuilder.publicId(new PublicId(restProduct.publicId));
    }

    if(restProduct.pictures != null && !restProduct.pictures.isEmpty()) {
      productBuilder.pictures(RestPicture.toDomain(restProduct.getPictures()));
    }

    return productBuilder.build();
  }

  public static RestProduct fromDomain(Product product) {
    return RestProductBuilder.restProduct()
      .brand(product.getProductBrand().value())
      .color(product.getColor().value())
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .featured(product.getFeatured())
      .category(RestCategory.fromDomain(product.getCategory()))
      .size(product.getSize())
      .pictures(RestPicture.fromDomain(product.getPictures()))
      .publicId(product.getPublicId().value())
      .nbInStock(product.getNbInStock())
      .build();
  }
}

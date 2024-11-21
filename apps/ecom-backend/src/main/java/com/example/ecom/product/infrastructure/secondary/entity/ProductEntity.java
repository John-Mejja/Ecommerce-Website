package com.example.ecom.product.infrastructure.secondary.entity;

import com.example.ecom.product.domain.aggregate.Product;
import com.example.ecom.product.domain.aggregate.ProductBuilder;
import com.example.ecom.product.domain.vo.*;
import com.example.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
  @SequenceGenerator(name = "productSequence", sequenceName = "product_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;


  @Column(name = "brand")
  private String brand;

  @Column(name = "color")
  private String color;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private double price;

  @Column(name = "featured")
  private boolean featured;

  @Enumerated(EnumType.STRING)
  @Column(name = "size")
  private ProductSize size;

  @Column(name = "publicId", unique = true)
  private UUID publicId;

  @Column(name = "nb_in_stock")
  private int nbInStock;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Set<PictureEntity> pictures = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "category_fk", referencedColumnName = "id")
  private CategoryEntity category;


  public static ProductEntity from(Product product) {
    ProductEntityBuilder productEntityBuilder = ProductEntityBuilder.productEntity();

    if(product.getDbId() != null) {
      productEntityBuilder.id(product.getDbId());
    }

    return productEntityBuilder
      .brand(product.getProductBrand().value())
      .color(product.getColor().value())
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .size(product.getSize())
      .publicId(product.getPublicId().value())
      .category(CategoryEntity.from(product.getCategory()))
      .pictures(PictureEntity.from(product.getPictures()))
      .featured(product.getFeatured())
      .nbInStock(product.getNbInStock())
      .build();
  }

  public static Product to(ProductEntity productEntity) {
    return ProductBuilder.product()
      .productBrand(new ProductBrand(productEntity.getBrand()))
      .color(new ProductColor(productEntity.getColor()))
      .description(new ProductDescription(productEntity.getDescription()))
      .name(new ProductName(productEntity.getName()))
      .price(new ProductPrice(productEntity.getPrice()))
      .size(productEntity.getSize())
      .publicId(new PublicId(productEntity.getPublicId()))
      .category(CategoryEntity.to(productEntity.getCategory()))
      .pictures(PictureEntity.to(productEntity.getPictures()))
      .featured(productEntity.getFeatured())
      .nbInStock(productEntity.getNbInStock())
      .build();
  }

  public boolean getFeatured() {
    return featured;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductEntity that)) return false;
    return featured == that.featured && Objects.equals(brand, that.brand) && Objects.equals(color, that.color) && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && size == that.size && Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, color, description, name, price, featured, size, publicId);
  }
}

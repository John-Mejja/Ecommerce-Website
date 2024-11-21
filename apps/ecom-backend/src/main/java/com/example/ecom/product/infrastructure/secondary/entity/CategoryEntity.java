package com.example.ecom.product.infrastructure.secondary.entity;


import com.example.ecom.product.domain.aggregate.Category;
import com.example.ecom.product.domain.aggregate.CategoryBuilder;
import com.example.ecom.product.domain.vo.CategoryName;
import com.example.ecom.product.domain.vo.PublicId;
import com.example.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
  @SequenceGenerator(name = "categorySequence", sequenceName = "product_category_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  @OneToMany(mappedBy = "category")
  private Set<ProductEntity> products;


  public static CategoryEntity from(Category category) {
    CategoryEntityBuilder categoryEntityBuilder = CategoryEntityBuilder.categoryEntity();

    if (category.getDbId() != null) {
      categoryEntityBuilder.id(category.getDbId());
    }
    return categoryEntityBuilder
      .name(category.getName().value())
      .publicId(category.getPublicId().value())
      .build();
  }

  public static Category to(CategoryEntity categoryEntity) {
    return CategoryBuilder.category()
      .dbId(categoryEntity.getId())
      .name(new CategoryName(categoryEntity.getName()))
      .publicId(new PublicId(categoryEntity.getPublicId()))
      .build();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}

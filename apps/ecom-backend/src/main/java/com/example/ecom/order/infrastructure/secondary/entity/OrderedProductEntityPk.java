package com.example.ecom.order.infrastructure.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductEntityPk implements Serializable {

  @ManyToOne
  @JoinColumn(name = "fk_order", nullable = false)
  private OrderEntity order;

  @Column(name = "fk_product", nullable = false)
  private UUID productPublicId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderedProductEntityPk that)) return false;
    return Objects.equals(productPublicId, that.productPublicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(productPublicId);
  }
}

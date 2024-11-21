package com.example.ecom.order.infrastructure.secondary.entity;

import com.example.ecom.order.domain.order.aggregate.Order;
import com.example.ecom.order.domain.order.aggregate.OrderBuilder;
import com.example.ecom.order.domain.order.aggregate.OrderedProduct;
import com.example.ecom.order.domain.order.vo.OrderStatus;
import com.example.ecom.product.domain.vo.PublicId;
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
import java.util.stream.Collectors;

@Entity
@Table(name = "order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequenceGenerator")
  @SequenceGenerator(name = "orderSequenceGenerator", sequenceName = "order_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "public_id", nullable = false, unique = true)
  private UUID publicId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @Column(name = "stripe_session_id", nullable = false)
  private String stripeSessionId;

  @OneToMany(mappedBy = "id.order", cascade = CascadeType.REMOVE)
  private Set<OrderedProductEntity> orderedProducts = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "fk_customer", nullable = false)
  private UserEntity user;

  public static OrderEntity from(Order order) {
    Set<OrderedProductEntity> orderedProductEntities = order.getOrderedProducts()
      .stream().map(OrderedProductEntity::from).collect(Collectors.toSet());

    return OrderEntityBuilder.orderEntity()
      .publicId(order.getPublicId().value())
      .status(order.getStatus())
      .stripeSessionId(order.getStripeId())
      .orderedProducts(orderedProductEntities)
      .user(UserEntity.from(order.getUser()))
      .build();
  }

  public static Order toDomain(OrderEntity orderEntity) {
    Set<OrderedProduct> orderedProducts = orderEntity.getOrderedProducts().stream()
      .map(OrderedProductEntity::toDomain).collect(Collectors.toSet());

    return OrderBuilder.order()
      .publicId(new PublicId(orderEntity.getPublicId()))
      .status(orderEntity.getStatus())
      .stripeId(orderEntity.getStripeSessionId())
      .user(UserEntity.toDomain(orderEntity.getUser()))
      .orderedProducts(orderedProducts.stream().toList())
      .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderEntity order)) return false;
    return Objects.equals(publicId, order.publicId) && status == order.status && Objects.equals(stripeSessionId, order.stripeSessionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(publicId, status, stripeSessionId);
  }
}

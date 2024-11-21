package com.example.ecom.order.domain.order.aggregate;

import com.example.ecom.order.domain.order.vo.OrderStatus;
import com.example.ecom.order.domain.order.vo.StripeSessionId;
import com.example.ecom.order.domain.user.aggregate.User;
import com.example.ecom.product.domain.vo.PublicId;
import lombok.Getter;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class Order {

  private OrderStatus status;

  private User user;

  private String stripeId;

  private PublicId publicId;

  private List<OrderedProduct> orderedProducts;

  public Order(OrderStatus status, User user, String stripeId, PublicId publicId, List<OrderedProduct> orderedProducts) {
    this.status = status;
    this.user = user;
    this.stripeId = stripeId;
    this.publicId = publicId;
    this.orderedProducts = orderedProducts;
  }

  public static Order create(User connectedUser, List<OrderedProduct> orderedProducts,
                       StripeSessionId stripeSessionId) {
    return OrderBuilder.order()
      .publicId(new PublicId(UUID.randomUUID()))
      .user(connectedUser)
      .status(OrderStatus.PENDING)
      .orderedProducts(orderedProducts)
      .stripeId(stripeSessionId.value())
      .build();
  }

  public void validatePayment() {
    this.status =  OrderStatus.PAID;
  }
}

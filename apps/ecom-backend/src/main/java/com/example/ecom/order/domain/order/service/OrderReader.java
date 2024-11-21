package com.example.ecom.order.domain.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ecom.order.domain.order.aggregate.Order;
import com.example.ecom.order.domain.order.repository.OrderRepository;
import com.example.ecom.order.domain.user.vo.UserPublicId;

public class OrderReader {

    private final OrderRepository orderRepository;
  
    public OrderReader(OrderRepository orderRepository) {
      this.orderRepository = orderRepository;
    }
  
    public Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable) {
      return orderRepository.findAllByUserPublicId(userPublicId, pageable);
    }
  
    public Page<Order> findAll(Pageable pageable) {
      return orderRepository.findAll(pageable);
    }
  }
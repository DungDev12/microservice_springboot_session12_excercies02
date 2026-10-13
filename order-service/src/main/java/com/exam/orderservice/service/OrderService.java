package com.exam.orderservice.service;

import com.exam.orderservice.dto.request.CreateOrderRequest;
import com.exam.orderservice.entity.Order;
import com.exam.orderservice.event.OrderCreatedEvent;
import com.exam.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setName(request.name());
        // 1. Lưu đơn hàng vào DB
        Order order = orderRepository.save(newOrder);

        // 2. Đóng gói event
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                request.customerEmail(),
                request.items().stream()
                        .map(item -> new OrderCreatedEvent.OrderItemEvent(
                                item.productId(),
                                item.quantity()
                        ))
                        .toList(),
                Instant.now()
        );

        // 3. Gửi event lên Kafka
        orderEventProducer.publish(event);

        return order;
    }
}

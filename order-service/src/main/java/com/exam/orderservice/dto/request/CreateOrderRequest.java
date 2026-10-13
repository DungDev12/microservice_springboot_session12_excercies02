package com.exam.orderservice.dto.request;

import com.exam.orderservice.event.OrderCreatedEvent;

import java.util.List;

public record CreateOrderRequest(
        String name,
        String customerEmail,
        List<OrderCreatedEvent.OrderItemEvent> items
) {
}

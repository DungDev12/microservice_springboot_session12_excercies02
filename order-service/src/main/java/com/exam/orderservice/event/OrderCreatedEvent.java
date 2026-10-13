package com.exam.orderservice.event;

import java.time.Instant;
import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        String customerEmail,
        List<OrderItemEvent> items,
        Instant createdAt
) {
    public record OrderItemEvent(
            Long productId,
            int quantity
    ){}
}

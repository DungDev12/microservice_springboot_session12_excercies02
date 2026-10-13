package com.exam.inventoryservice.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "inventory-group"
    )
    public void consume(String message) throws Exception {
        System.out.println(
                "Đã xử lý trừ kho cho đơn hàng: " + message
        );
    }
}

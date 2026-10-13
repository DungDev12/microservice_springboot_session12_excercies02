package com.exam.orderservice.service;

import com.exam.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String TOPIC = "order-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(OrderCreatedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    TOPIC,
                    event.orderId().toString(),
                    json
            );
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Không thể gửi OrderCreatedEvent lên Kafka", e
            );
        }
    }
}

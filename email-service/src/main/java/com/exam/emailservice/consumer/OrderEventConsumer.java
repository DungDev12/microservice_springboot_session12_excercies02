package com.exam.emailservice.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "email-group"
    )
    public void consume(String message) throws Exception {
        System.out.println(
                "Gửi email xác nhận đơn hàng: " + message
        );

        // TODO: Gọi EmailService để gửi email thực tế.
    }
}

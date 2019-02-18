package com.example.game.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

//@Service
//@EnableAsync

public class KafkaConsumer {

    @KafkaListener(topics = "topic")
    @Async
    public void listenWithHeaders(
            @Payload String message) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(
                "Received Message: " + message);
    }



}

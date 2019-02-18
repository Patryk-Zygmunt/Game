package com.example.game.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class KakaWorkerApplication implements CommandLineRunner  {

    @Autowired
    KafkaProducer kafkaProducer;


    public static void main(String[] args) {
        SpringApplication.run(KakaWorkerApplication.class, args);
    }


    @Override
    public void run(String... args) {
        while(true) {

            Scanner sc = new Scanner(System.in);

            kafkaProducer.kafkaTemplate.send("topic", sc.nextLine());


        }
    }

}


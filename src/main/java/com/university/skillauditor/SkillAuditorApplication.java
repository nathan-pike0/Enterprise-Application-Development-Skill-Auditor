package com.university.skillauditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@EnableRabbit
@EnableAsync
@EnableRetry
@SpringBootApplication
public class SkillAuditorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillAuditorApplication.class, args);
    }
}
package com.kb.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LearnSpringBoot3SecurityApplication {

    public static void main(String[] args) {
        try {

            SpringApplication.run(LearnSpringBoot3SecurityApplication.class, args);
        } catch (Exception ex) {
            log.error("Application failed to start due to an exception occurred.", ex);
        }
    }

}

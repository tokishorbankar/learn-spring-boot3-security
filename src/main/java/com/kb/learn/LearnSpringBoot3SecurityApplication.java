package com.kb.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class LearnSpringBoot3SecurityApplication {

    public static void main(String[] args) {
        log.debug("Starting security application with arguments {}", Arrays.toString(args));
        SpringApplication.run(LearnSpringBoot3SecurityApplication.class, args);
    }

}

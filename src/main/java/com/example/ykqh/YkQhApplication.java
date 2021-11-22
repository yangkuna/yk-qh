package com.example.ykqh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yk
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class YkQhApplication {

    public static void main(String[] args) {
        SpringApplication.run(YkQhApplication.class, args);
        System.out.println("Application is start");
    }

}

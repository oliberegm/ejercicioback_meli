package com.cloud.meli.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class ChallengeMeliApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeMeliApplication.class, args);
    }

}

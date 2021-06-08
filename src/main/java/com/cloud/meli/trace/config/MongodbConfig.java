package com.cloud.meli.trace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.cloud.meli.trace.repository")
public class MongodbConfig {
}

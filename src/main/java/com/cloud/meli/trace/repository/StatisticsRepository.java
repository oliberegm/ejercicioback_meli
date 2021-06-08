package com.cloud.meli.trace.repository;

import com.cloud.meli.trace.model.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    Optional<Statistics> findByName(String name);

    Optional<Statistics> findByCode3(String code3);

}

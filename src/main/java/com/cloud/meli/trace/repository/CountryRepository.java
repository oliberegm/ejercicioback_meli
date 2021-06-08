package com.cloud.meli.trace.repository;

import com.cloud.meli.trace.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CountryRepository extends MongoRepository<Country, String> {
    Optional<Country> findByName(String name);

    Optional<Country> findByCode3(String code);
}

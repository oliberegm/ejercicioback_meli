package com.cloud.meli.trace.repository;

import com.cloud.meli.trace.model.Currency;
import com.cloud.meli.trace.service.impl.ExternalRequestsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
    @Cacheable(value = ExternalRequestsService.SEARCHCURRENCY_CACHE_KEY, unless="#result == null")
    Optional<Currency> findByCountry(String country);

}

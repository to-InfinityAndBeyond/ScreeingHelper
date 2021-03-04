package com.ScreeningHelper.Beyond.ScreeningHelper.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoStockValueRepository extends MongoRepository<MongoStockValue, String> {
    Optional<MongoStockValue> findById(String id);
}

package com.ScreeningHelper.Beyond.ScreeningHelper.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoPykrxRepository extends MongoRepository<MongoPykrxInfo, String> {
    Optional<MongoPykrxInfo> findById(String id);
}

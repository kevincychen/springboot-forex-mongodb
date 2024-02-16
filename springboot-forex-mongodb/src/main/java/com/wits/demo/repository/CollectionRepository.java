package com.wits.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wits.demo.dto.Collection;

public interface CollectionRepository extends MongoRepository<Collection, String> {

}
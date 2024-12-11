package com.lcvl.microservices.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lcvl.microservices.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
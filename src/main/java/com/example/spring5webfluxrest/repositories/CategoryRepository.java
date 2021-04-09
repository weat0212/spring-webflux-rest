package com.example.spring5webfluxrest.repositories;

import com.example.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author I-Chung, Wang
 * @date 2021/4/9 下午 01:22
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}

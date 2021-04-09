package com.example.spring5webfluxrest.repositories;

import com.example.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author I-Chung, Wang
 * @date 2021/4/9 下午 01:35
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}

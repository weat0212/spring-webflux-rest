package com.example.spring5webfluxrest.bootstrap;

import com.example.spring5webfluxrest.domain.Category;
import com.example.spring5webfluxrest.domain.Vendor;
import com.example.spring5webfluxrest.repositories.CategoryRepository;
import com.example.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author I-Chung, Wang
 * @date 2021/4/9 下午 01:40
 */

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {

            //load data
            System.out.println("LOADING DATA ON BOOTSTRAP");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Andy").lastName("Wang").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Elon").lastName("Musk").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Danny").lastName("Wang").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Cheryl").lastName("Chu").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Sea").lastName("Bear").build()).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());

        }
    }
}

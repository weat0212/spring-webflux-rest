package com.example.spring5webfluxrest.controllers;

import com.example.spring5webfluxrest.domain.Vendor;
import com.example.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



/**
 * @author I-Chung, Wang
 * @date 2021/4/11 下午 02:44
 */

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {

        Vendor vendorOrigin = vendorRepository.findById(id).block();
        Boolean changed = false;

        if (!vendorOrigin.getFirstName().equals(vendor.getFirstName())) {
            vendorOrigin.setFirstName(vendor.getFirstName());
            changed = true;
        }

        if (!vendorOrigin.getLastName().equals(vendor.getLastName())) {
            vendorOrigin.setLastName(vendor.getLastName());
            changed = true;
        }

        if (changed) {
            return vendorRepository.save(vendorOrigin);
        } else {
            return Mono.just(vendorOrigin);
        }
    }
}

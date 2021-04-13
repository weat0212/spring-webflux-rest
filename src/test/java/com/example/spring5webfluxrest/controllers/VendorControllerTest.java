package com.example.spring5webfluxrest.controllers;

import com.example.spring5webfluxrest.domain.Vendor;
import com.example.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author I-Chung, Wang
 * @date 2021/4/11 下午 02:48
 */

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {

        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Andy").build(),
                        Vendor.builder().firstName("Alex").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {

        given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().firstName("Andy").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void testCreate() {

        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdate() {

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Andy").build());

        webTestClient.put()
                .uri("/api/v1/vendors/123")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testPatch() {

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Andy").lastName("Wang").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMonoToSave = Mono.just(Vendor.builder().firstName("Candy").lastName("Wang").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/abc")
                .body(vendorMonoToSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    void testPatchNoChanges() {

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Andy").lastName("Wang").build()));

//        given(vendorRepository.save(any(Vendor.class)))
//                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMonoToSave = Mono.just(Vendor.builder().firstName("Andy").lastName("Wang").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/123")
                .body(vendorMonoToSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());
    }
}
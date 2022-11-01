package com.nttdata.bootcamp.CustomerProduct.application;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerActiveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("${message.path-customerProductActives}")
public class CustomerActiveProductController {
    @Autowired
    private ICustomerActiveProductService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerActiveProductResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<CustomerActiveProductResponse> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<CustomerActiveProductResponse> save(@RequestBody CustomerActiveProductRequest request) {
        return service.save(request);
    }

    @PutMapping("/update/{id}")
    public Mono<CustomerActiveProductResponse> update(@RequestBody CustomerActiveProductRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<CustomerActiveProductResponse> delete(@PathVariable String id) {
        return service.delete(id);
    }

}

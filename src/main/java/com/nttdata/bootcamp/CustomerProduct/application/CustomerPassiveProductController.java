package com.nttdata.bootcamp.CustomerProduct.application;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerPassiveProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-customerProductPassives}")
@RefreshScope
public class CustomerPassiveProductController {
    @Autowired
    private ICustomerPassiveProductService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerPassiveProductResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<CustomerPassiveProductResponse> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<CustomerPassiveProductResponse> save(@RequestBody CustomerPassiveProductRequest request) {
        return service.save(request);
    }

    @PutMapping("/update/{id}")
    public Mono<CustomerPassiveProductResponse> update(@RequestBody CustomerPassiveProductRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<CustomerPassiveProductResponse> delete(@PathVariable String id) {
        return service.delete(id);
    }

}

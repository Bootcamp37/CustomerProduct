package com.nttdata.bootcamp.CustomerProduct.application;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerPassiveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-customerProductPassives}")
@RefreshScope
@Slf4j
public class CustomerPassiveProductController {
    @Autowired
    private ICustomerPassiveProductService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerPassiveProductResponse> getAll() {
        log.info("====> CustomerPassiveProductController: GetAll");
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Mono<CustomerPassiveProductResponse>> getById(@PathVariable String id) {
        log.info("====> CustomerPassiveProductController: GetById");
        Mono<CustomerPassiveProductResponse> customerPassiveProductResponse = service.getById(id);
        return new ResponseEntity<>(customerPassiveProductResponse, customerPassiveProductResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerPassiveProductResponse> save(@RequestBody CustomerPassiveProductRequest request) {
        log.info("====> CustomerPassiveProductController: Save");
        return service.save(Mono.just(request));
    }

    @PutMapping("/update/{id}")
    public Mono<CustomerPassiveProductResponse> update(@RequestBody CustomerPassiveProductRequest request, @PathVariable String id) {
        log.info("====> CustomerPassiveProductController: Update");
        return service.update(Mono.just(request), id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("====> CustomerPassiveProductController: Delete");
        return service.delete(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

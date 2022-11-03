package com.nttdata.bootcamp.CustomerProduct.application;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerActiveProductService;
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
@Slf4j
@RequestMapping("${message.path-customerProductActives}")
@RefreshScope
public class CustomerActiveProductController {
    @Autowired
    private ICustomerActiveProductService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerActiveProductResponse> getAll() {
        log.debug("====> CustomerActiveProductController: GetAll");
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Mono<CustomerActiveProductResponse>> getById(@PathVariable String id) {
        log.debug("====> CustomerActiveProductController: GetById");
        Mono<CustomerActiveProductResponse> customerActiveProductResponseMono = service.getById(id);
        return new ResponseEntity<>(customerActiveProductResponseMono, customerActiveProductResponseMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerActiveProductResponse> save(@RequestBody CustomerActiveProductRequest request) {
        log.debug("====> CustomerActiveProductController: Save");
        return service.save(Mono.just(request));
    }

    @PutMapping("/update/{id}")
    public Mono<CustomerActiveProductResponse> update(@RequestBody CustomerActiveProductRequest request, @PathVariable String id) {
        log.debug("====> CustomerActiveProductController: Update");
        return service.update(Mono.just(request), id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.debug("====> CustomerActiveProductController: Delete");
        return service.delete(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

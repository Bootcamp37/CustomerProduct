package com.nttdata.bootcamp.customerproduct.application;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.customerproduct.infraestructure.service.ICustomerActiveProductService;
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

/**
 * Controlador REST para el manejo de los CUSTOMERACTIVEPRODUCT.
 * <p>El path para la llamada de estos metodos se encuentra
 * seteado en la variable message.path-customerProductActives</p>
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("${message.path-customerProductActives}")
@RefreshScope
public class CustomerActiveProductController {
  @Autowired
  private ICustomerActiveProductService service;

  /**
   * Método GetAll.
   * <p>Este metodo devuelve la lista completa de CUSTOMERACTIVEPRODUCT
   * guardados en la BD.</p>
   *
   * @return Flux < CustomerResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Flux<CustomerActiveProductResponse> getAll() {
    log.info("====> CustomerActiveProductController: GetAll");
    return service.getAll();
  }

  /**
   * Método GetById.
   * <p>Este metodo devuelve un CUSTOMERACTIVEPRODUCT que
   * tenga el ID recibido en la url.</p>
   *
   * @param id Es la llave para buscar el CUSTOMERACTIVEPRODUCT
   * @return ResponseEntity < Mono < CustomerActiveProductResponse > >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @GetMapping(path = "/{id}")
  @ResponseBody
  public ResponseEntity<Mono<CustomerActiveProductResponse>> getById(@PathVariable String id) {
    log.info("====> CustomerActiveProductController: GetById");
    Mono<CustomerActiveProductResponse> customerActiveProductResponseMono = service.getById(id);
    return new ResponseEntity<>(customerActiveProductResponseMono,
          customerActiveProductResponseMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }

  /**
   * Método Save.
   *
   * <p>Este metodo se encarga de guardar un CUSTOMERACTIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param request Objeto del tipo CustomerActiveProductRequest para guardar
   * @return Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CustomerActiveProductResponse> save(
        @RequestBody CustomerActiveProductRequest request) {
    log.info("====> CustomerActiveProductController: Save");
    return service.save(Mono.just(request));
  }

  /**
   * Método Update.
   *
   * <p>Este metodo se encarga de guardar un CUSTOMERACTIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param request Objeto del tipo CustomerActiveProductRequest para editar
   * @param id      Es la llave para buscar el CUSTOMERACTIVEPRODUCT
   * @return Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @PutMapping("/update/{id}")
  public Mono<CustomerActiveProductResponse> update(
        @RequestBody CustomerActiveProductRequest request,
        @PathVariable String id) {
    log.info("====> CustomerActiveProductController: Update");
    return service.update(Mono.just(request), id);
  }

  /**
   * Método Delete.
   *
   * <p>Este metodo se encarga de eliminar un CUSTOMERACTIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param id Es la llave para buscar el CUSTOMER
   * @return Mono < ResponseEntity < Void > >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("====> CustomerActiveProductController: Delete");
    return service.delete(id)
          .map(r -> ResponseEntity.ok().<Void>build())
          .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}

package com.nttdata.bootcamp.customerproduct.application;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.customerproduct.infraestructure.service.ICustomerPassiveProductService;
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
 * Controlador REST para el manejo de los CUSTOMERPASSIVEPRODUCT.
 * <p>El path para la llamada de estos metodos se encuentra
 * seteado en la variable message.path-customerProductActives</p>
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-customerProductPassives}")
@RefreshScope
@Slf4j
public class CustomerPassiveProductController {
  @Autowired
  private ICustomerPassiveProductService service;

  /**
   * Método GetAll.
   * <p>Este metodo devuelve la lista completa de CUSTOMERPASSIVEPRODUCT
   * guardados en la BD.</p>
   *
   * @return Flux < CustomerPassiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Flux<CustomerPassiveProductResponse> getAll() {
    log.info("====> CustomerPassiveProductController: GetAll");
    return service.getAll();
  }

  /**
   * Método GetById.
   * <p>Este metodo devuelve un CUSTOMERPASSIVEPRODUCT que
   * tenga el ID recibido en la url.</p>
   *
   * @param id Es la llave para buscar el CUSTOMERPASSIVEPRODUCT
   * @return ResponseEntity < Mono < CustomerPassiveProductResponse > >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @GetMapping(path = "/{id}")
  @ResponseBody
  public ResponseEntity<Mono<CustomerPassiveProductResponse>> getById(@PathVariable String id) {
    log.info("====> CustomerPassiveProductController: GetById");
    Mono<CustomerPassiveProductResponse> customerPassiveProductResponse
          = service.getById(id);
    return new ResponseEntity<>(customerPassiveProductResponse,
          customerPassiveProductResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }

  /**
   * Método Save.
   *
   * <p>Este metodo se encarga de guardar un CUSTOMERPASSIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param request Objeto del tipo CustomerPassiveProductRequest para guardar
   * @return Mono < CustomerPassiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CustomerPassiveProductResponse> save(
        @RequestBody CustomerPassiveProductRequest request) {
    log.info("====> CustomerPassiveProductController: Save");
    return service.save(Mono.just(request));
  }

  /**
   * Método Update.
   *
   * <p>Este metodo se encarga de guardar un CUSTOMERPASSIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param request Objeto del tipo CustomerActiveProductRequest para editar
   * @param id      Es la llave para buscar el CUSTOMERPASSIVEPRODUCT
   * @return Mono < CustomerPassiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @PutMapping("/update/{id}")
  public Mono<CustomerPassiveProductResponse> update(
        @RequestBody CustomerPassiveProductRequest request,
        @PathVariable String id) {
    log.info("====> CustomerPassiveProductController: Update");
    return service.update(Mono.just(request), id);
  }

  /**
   * Método Delete.
   *
   * <p>Este metodo se encarga de eliminar un CUSTOMERPASSIVEPRODUCT
   * en la base de datos.</p>
   *
   * @param id Es la llave para buscar el CUSTOMERPASSIVEPRODUCT
   * @return Mono < ResponseEntity < Void > >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("====> CustomerPassiveProductController: Delete");
    return service.delete(id)
          .map(r -> ResponseEntity.ok().<Void>build())
          .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}

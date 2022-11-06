package com.nttdata.bootcamp.customerproduct.domain.dto;

import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerPassiveProduct;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductSubType;

/**
 * Clase Factory para el manejo de los CUSTOMERS.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
public class PassiveProductFactory {
  /**
   * Método build.
   *
   * @param passiveProduct Variable del tipo CustomerPassiveProductRequest
   * @param productSubType Variable del tipo ProductSubType
   * @return Retorna un CustomerPassiveProduct
   */
  public static CustomerPassiveProduct build(CustomerPassiveProductRequest passiveProduct,
                                             ProductSubType productSubType) {
    CustomerPassiveProduct customerPassiveProduct = new CustomerPassiveProduct();
    if (validatedSaving(passiveProduct, productSubType)) {
      customerPassiveProduct.setMovementDay(0);
      customerPassiveProduct.setMaintenance(0.0);
      customerPassiveProduct.setMovementLimit(passiveProduct.getMovementLimit());
      customerPassiveProduct.setMinAmount(passiveProduct.getMinAmount());
    } else if (validatedCurrent(passiveProduct, productSubType)) {
      customerPassiveProduct.setMovementDay(0);
      customerPassiveProduct.setMaintenance(passiveProduct.getMaintenance());
      customerPassiveProduct.setMovementLimit(0);
      customerPassiveProduct.setMinAmount(0.0);
    } else if (validatedFix(passiveProduct, productSubType)) {
      customerPassiveProduct.setMovementDay(passiveProduct.getMovementDay());
      customerPassiveProduct.setMaintenance(0.0);
      customerPassiveProduct.setMovementLimit(1);
      customerPassiveProduct.setMinAmount(0.0);
    }
    customerPassiveProduct.setCustomerId(passiveProduct.getCustomerId());
    customerPassiveProduct.setProductId(passiveProduct.getProductId());
    customerPassiveProduct.setAmount(passiveProduct.getAmount());
    customerPassiveProduct.setMaxMovementFree(passiveProduct.getMaxMovementFree());
    customerPassiveProduct.setCommission(passiveProduct.getCommission());
    return customerPassiveProduct;
  }

  // Ahorro
  // mantenimiento = 0 && Movimientos > 0
  private static boolean validatedSaving(CustomerPassiveProductRequest passiveProduct,
                                         ProductSubType productResponse) {
    return productResponse.equals(ProductSubType.SAVING)
          && passiveProduct.getMaintenance() == 0
          && passiveProduct.getMovementLimit() > 0;
  }

  // Cuenta corriente
  // mantenimiento > 0 && Movimientos = 0
  private static boolean validatedCurrent(CustomerPassiveProductRequest passiveProduct,
                                          ProductSubType productResponse) {
    return productResponse.equals(ProductSubType.CURRENT)
          && passiveProduct.getMaintenance() > 0
          && passiveProduct.getMovementLimit() == 0;
  }

  // Plazo Fijo
  // mantenimiento = 0 && Movimiento = 1 && Dia del mes
  private static boolean validatedFix(CustomerPassiveProductRequest passiveProduct,
                                      ProductSubType productResponse) {
    return productResponse.equals(ProductSubType.FIX)
          && passiveProduct.getMaintenance() == 0
          && passiveProduct.getMovementLimit() == 1
          && passiveProduct.getMovementDay() > 0
          && passiveProduct.getMovementDay() < 30;
  }
}

package org.springBootAngular.service;

import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.SupplierDTO;

public interface SupplierService {
    Response saveSupplier(SupplierDTO supplierDTO);
    Response getAllSuppliers();
    Response getSupplierById(Long id);
    Response updateSupplier(Long id, SupplierDTO supplierDto);
    Response deleteSupplier(Long id);
}

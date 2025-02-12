package org.springBootAngular.controller;

import lombok.RequiredArgsConstructor;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.SupplierDTO;
import org.springBootAngular.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> addSupplier(@RequestBody SupplierDTO supplierDTO){
        return ResponseEntity.ok(supplierService.saveSupplier(supplierDTO));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> allSupplier(){
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("supplier/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> findSupplierById(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO){
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> deleteSupplier(@PathVariable Long id){
            return ResponseEntity.ok(supplierService.deleteSupplier(id));

    }
}

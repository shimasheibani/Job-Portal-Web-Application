package org.springBootAngular.controller;

import lombok.RequiredArgsConstructor;
import org.springBootAngular.dto.ProductDTO;
import org.springBootAngular.dto.Response;
import org.springBootAngular.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")

public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> saveProduct(
            @RequestParam("imgFile") MultipartFile imgFile,
            @RequestParam("name") String name,
            @RequestParam("sku") String sku,
            @RequestParam("price")BigDecimal price,
            @RequestParam("stockQuality") Integer stockQuality,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "description", required = false) String description){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuality(stockQuality);
        productDTO.setCategoryId(categoryId);
        productDTO.setDecription(description);
        return ResponseEntity.ok(productService.saveProduct(productDTO, imgFile));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }
    @GetMapping("/product/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getProfuctById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
            @RequestParam(value = "name" , required = false) String name,
            @RequestParam(value = "sku" , required = false) String sku,
            @RequestParam(value ="price" , required = false)BigDecimal price,
            @RequestParam(value ="stockQuality"  , required = false) Integer stockQuality,
            @RequestParam(value ="categoryId" , required = false) Long categoryId,
            @RequestParam(value ="productId" , required = true) Long productId,
            @RequestParam(value = "description", required = false) String description){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setProductId(productId);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuality(stockQuality);
        productDTO.setCategoryId(categoryId);
        productDTO.setDecription(description);
        return ResponseEntity.ok(productService.updateProduct(productDTO, imgFile));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}

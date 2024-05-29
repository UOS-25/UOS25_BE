package com.uos25.uos25.products.api;

import com.uos25.uos25.products.dto.ProductsDTO;
import com.uos25.uos25.products.service.ProductsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Products API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final ProductsService productsService;


    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductsDTO productsDTO){
        System.out.println(productsDTO);
        productsService.saveProducts(productsDTO);
        System.out.println(productsDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updatePrice")
    public ResponseEntity<?> updatePrice(@RequestBody ProductsDTO productsDTO){
        productsService.updateProductPrice(productsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        productsService.deleteProduct(productCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-product-code/{productCode}")
    public ResponseEntity<ProductsDTO> findByProductCode(@PathVariable String productCode) {
        ProductsDTO productDTO = productsService.findByProductCode(productCode);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/by-product-name/{productName}")
    public ResponseEntity<ProductsDTO> findByProductName(@PathVariable String productName) {
        ProductsDTO productDTO = productsService.findByProductName(productName);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        List<ProductsDTO> products = productsService.findAll();
        return ResponseEntity.ok(products);
    }

}

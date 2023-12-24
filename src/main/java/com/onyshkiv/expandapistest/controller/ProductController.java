package com.onyshkiv.expandapistest.controller;

import com.onyshkiv.expandapistest.entity.Product;
import com.onyshkiv.expandapistest.entity.ProductJsonPayload;
import com.onyshkiv.expandapistest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public void createTableAndAddProducts(@RequestBody ProductJsonPayload payload) {
        productService.createTable(payload.getTable());
        productService.save(payload.getProducts());
    }

    @GetMapping("/get")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();

    }
}


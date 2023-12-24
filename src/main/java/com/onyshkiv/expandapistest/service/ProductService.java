package com.onyshkiv.expandapistest.service;

import com.onyshkiv.expandapistest.entity.Product;
import com.onyshkiv.expandapistest.repository.ProductRepository;
import com.onyshkiv.expandapistest.repository.ProductsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductsDao productsDao;

    @Transactional
    public void createTable(String tableName) {
        productsDao.createTable(tableName);
    }

    @Transactional
    public void save(List<Product> products) {
        productRepository.saveAll(products);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


}

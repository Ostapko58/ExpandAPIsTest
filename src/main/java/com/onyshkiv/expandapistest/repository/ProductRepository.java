package com.onyshkiv.expandapistest.repository;

import com.onyshkiv.expandapistest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}

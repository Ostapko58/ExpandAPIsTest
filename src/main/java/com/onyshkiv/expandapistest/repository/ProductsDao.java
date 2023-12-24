package com.onyshkiv.expandapistest.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductsDao {
    private final EntityManager entityManager;

    public void createTable(String tableName) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "entry_date DATE NOT NULL," +
                "item_quantity INTEGER NOT NULL," +
                "item_code VARCHAR(255) NOT NULL," +
                "item_name VARCHAR(255) NOT NULL," +
                "status VARCHAR(255) NOT NULL" +
                ")", tableName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}

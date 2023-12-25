package org.example.test.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.test.models.Product;
import org.example.test.repositories.ProductRepository;
import org.example.test.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void saveProducts(String tableName, List<Product> products) {
        createTableIfNotExists(tableName);
        productRepository.saveAll(products);

    }

    private void createTableIfNotExists(String tableName) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id SERIAL PRIMARY KEY," +
                "entry_date VARCHAR(255)," +
                "item_code VARCHAR(255)," +
                "item_name VARCHAR(255)," +
                "item_quantity VARCHAR(255)," +
                "status VARCHAR(255))";

        entityManager.createNativeQuery(createTableQuery).executeUpdate();

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

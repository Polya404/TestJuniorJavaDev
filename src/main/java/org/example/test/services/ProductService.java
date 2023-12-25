package org.example.test.services;

import org.example.test.models.Product;

import java.util.List;

public interface ProductService {
    void saveProducts(String tableName, List<Product> products);
    List<Product> getAllProducts();
}

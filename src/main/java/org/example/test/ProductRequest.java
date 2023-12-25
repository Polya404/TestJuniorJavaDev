package org.example.test;

import lombok.Data;
import org.example.test.models.Product;

import java.util.List;

@Data
public class ProductRequest {
    private String table;
    private List<Product> records;
}

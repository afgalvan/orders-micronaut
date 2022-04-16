package tech.afgalvan.products.services;

import tech.afgalvan.products.models.Product;

import java.util.List;

public interface ProductsService {
    Product saveProduct(Product product);

    List<Product> getProducts();

    Product getProductById(int id);
}

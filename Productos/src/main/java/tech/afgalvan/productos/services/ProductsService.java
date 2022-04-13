package tech.afgalvan.productos.services;

import tech.afgalvan.productos.models.Product;

import java.util.List;

public interface ProductsService {
    Product saveProduct(Product product);

    List<Product> getProducts();
}

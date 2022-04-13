package tech.afgalvan.productos.services;

import jakarta.inject.Singleton;
import tech.afgalvan.productos.data.ProductsRepository;
import tech.afgalvan.productos.models.Product;

@Singleton
public class ProductsServiceImp implements ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsServiceImp(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product saveProduct(Product product) {
        return productsRepository.save(product);
    }
}

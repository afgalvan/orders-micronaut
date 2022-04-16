package tech.afgalvan.products.services;

import jakarta.inject.Singleton;
import tech.afgalvan.products.data.ProductsRepository;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class ProductsServiceImp implements ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsServiceImp(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product saveProduct(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return StreamSupport.stream(productsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(int id) throws ProductNotFoundException {
        return productsRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
    }
}

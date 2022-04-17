package tech.afgalvan.products.services;

import jakarta.inject.Singleton;
import tech.afgalvan.products.infrastructure.persistence.ProductsRepository;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

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
        return productsRepository.asList();
    }

    @Override
    public Product getProductById(int id) throws ProductNotFoundException {
        return productsRepository.findById(id)
                .orElseThrow(this::throwNotFoundException);
    }

    private ProductNotFoundException throwNotFoundException() {
        return new ProductNotFoundException("Producto no encontrado");
    }

    public boolean deleteProductById(int id) {
        try {
            productsRepository.delete(getProductById(id));
            return true;
        } catch (ProductNotFoundException e) {
            return false;
        }
    }
}

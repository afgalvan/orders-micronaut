package tech.afgalvan.products.unit.services;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.products.data.ProductsRepository;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsServiceImp;
import tech.afgalvan.products.unit.stubs.ProductStub;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductsServiceTest {
    @Inject
    ProductsServiceImp productsService;
    @Inject
    ProductsRepository productsRepository;

    @Test
    void testProductIdIsRetrievedCorrectly() {
        Product product = ProductStub.DEFAULT;
        when(productsRepository.save(product)).then(invocation -> ProductStub.getStoredProductAnswer());

        Product storedProduct = productsService.saveProduct(product);
        assertEquals(ProductStub.getStoredProductAnswer(), storedProduct);
        verify(productsRepository).save(product);
    }

    @Test
    void testProductIsStoredCorrectly() {
        when(productsRepository.findAll()).then(invocation -> ProductStub.getProductsAnswer());
        assertEquals(ProductStub.getProductsAnswer(), productsService.getProducts());
        verify(productsRepository).findAll();
    }

    @Test
    void testThatProductCanBeFound() {
        when(productsRepository.findById(any(Integer.class)))
                .then(invocation -> Optional.of(ProductStub.getStoredProductAnswer()));
        Product product = productsService.getProductById(1);
        assertEquals(ProductStub.getStoredProductAnswer(), product);
        verify(productsRepository).findById(1);
    }

    @Test
    void testThatProductCantBeFound() {
        when(productsRepository.findById(any(Integer.class)))
                .then(invocation -> Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productsService.getProductById(1));
        verify(productsRepository).findById(1);
    }

    @MockBean(ProductsRepository.class)
    ProductsRepository productRepository() {
        return mock(ProductsRepository.class);
    }
}

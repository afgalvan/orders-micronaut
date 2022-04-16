package tech.afgalvan.products.unit.services;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.products.data.ProductsRepository;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsServiceImp;
import tech.afgalvan.products.shared.stubs.ProductStub;

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
    void testProductIsStoredCorrectly() {
        when(productsRepository.save(ProductStub.DEFAULT))
            .then(invocation -> ProductStub.getStoredProductAnswer());

        final Product storedProduct = productsService.saveProduct(ProductStub.DEFAULT);
        assertEquals(ProductStub.getStoredProductAnswer(), storedProduct);
        verify(productsRepository).save(ProductStub.DEFAULT);
    }

    @Test
    void testProductsAreRetrievedCorrectly() {
        when(productsRepository.asList())
            .then(invocation -> ProductStub.getProductsAnswer());
        assertEquals(ProductStub.getProductsAnswer(), productsService.getProducts());
        verify(productsRepository).asList();
    }

    @Test
    void testThatProductCanBeFound() {
        when(productsRepository.findById(any(Integer.class)))
                .then(invocation -> Optional.of(ProductStub.getStoredProductAnswer()));
        assertEquals(ProductStub.getStoredProductAnswer(), productsService.getProductById(1));
        verify(productsRepository).findById(1);
    }

    @Test
    void testThatProductCanNotBeFound() {
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

package tech.afgalvan.products.unit.services;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.afgalvan.products.infrastructure.persistence.ProductsRepository;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsServiceImp;
import tech.afgalvan.products.shared.stubs.ProductStub;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
            .thenReturn(ProductStub.getStoredProductAnswer());

        final Product storedProduct = productsService.saveProduct(ProductStub.DEFAULT);
        assertEquals(ProductStub.getStoredProductAnswer(), storedProduct);
        verify(productsRepository).save(ProductStub.DEFAULT);
    }

    @Test
    void testProductsAreRetrievedCorrectly() {
        when(productsRepository.asList())
            .thenReturn(ProductStub.getProductsAnswer());
        assertEquals(ProductStub.getProductsAnswer(), productsService.getProducts());
        verify(productsRepository).asList();
    }

    @Test
    void testThatProductCanBeFound() {
        when(productsRepository.findById(any(Integer.class)))
            .thenReturn(Optional.of(ProductStub.getStoredProductAnswer()));
        assertEquals(ProductStub.getStoredProductAnswer(), productsService.getProductById(1));
        verify(productsRepository).findById(1);
    }

    @Test
    void testThatProductCanNotBeFound() {
        when(productsRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productsService.getProductById(1));
        verify(productsRepository).findById(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testThatProductCanBeDeleted(int id) {
        when(productsRepository.findById(any(Integer.class)))
            .thenReturn(Optional.of(ProductStub.getStoredProductAnswer()));
        doNothing().when(productsRepository).delete(any(Product.class));

        assertTrue(productsService.deleteProductById(id));

        verify(productsRepository).delete(any(Product.class));
        verify(productsRepository).findById(any(Integer.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testThatNonExistingProductCanNotBeDeleted(int id) {
        when(productsRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());

        assertFalse(productsService.deleteProductById(id));
        verify(productsRepository).findById(any(Integer.class));
        verify(productsRepository, never()).delete(any(Product.class));
    }

    @MockBean(ProductsRepository.class)
    ProductsRepository productRepository() {
        return mock(ProductsRepository.class);
    }
}

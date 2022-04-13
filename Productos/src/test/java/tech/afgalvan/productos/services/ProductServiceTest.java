package tech.afgalvan.productos.services;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.productos.data.ProductsRepository;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.stubs.ProductStubs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductServiceTest {
    @Inject
    ProductsServiceImp productService;
    @Inject
    ProductsRepository productsRepository;

    @Test
    void testProductIsSavedCorrectly() {
        var price = 4000;
        var product = new Product("name", price);
        when(productsRepository.save(product)).then(invocation -> ProductStubs.getStoredProductAnswer(product));

        Product storedProduct = productService.saveProduct(product);
        assertEquals(ProductStubs.getStoredProductAnswer(product), storedProduct);
        verify(productsRepository).save(product);
    }

    @MockBean(ProductsRepository.class)
    ProductsRepository productRepository() {
        return mock(ProductsRepository.class);
    }
}

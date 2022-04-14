package tech.afgalvan.productos.unit.services;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import tech.afgalvan.productos.data.ProductsRepository;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.services.ProductsServiceImp;
import tech.afgalvan.productos.unit.stubs.ProductStub;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductsServiceTest {
    @Inject
    ProductsServiceImp productsService;
    @Inject
    ProductsRepository productsRepository;

    private final List<Product> products = new ArrayList<>();

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
        Product product = ProductStub.DEFAULT;
        when(productsRepository.save(product)).then(this::addProductMock);
        when(productsRepository.findAll()).then(invocation -> ProductStub.getProductsAnswer());

        productsService.saveProduct(product);
        assertEquals(products, productsService.getProducts());
        verify(productsRepository).findAll();
    }

    Product addProductMock(InvocationOnMock invocation) {
        Product productWithId = ProductStub.getStoredProductAnswer();
        products.add(productWithId);
        return productWithId;
    }

    @MockBean(ProductsRepository.class)
    ProductsRepository productRepository() {
        return mock(ProductsRepository.class);
    }
}

package tech.afgalvan.products.unit.controllers;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsService;
import tech.afgalvan.products.unit.stubs.ProductStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductsControllerTest {
    @Inject
    ProductsService productsService;

    @Inject
    @Client("/api/products")
    HttpClient client;

    @Test
    void testProductPostRequest() {
        when(productsService.saveProduct(any(Product.class))).then(invocation -> ProductStub.getStoredProductAnswer());
        HttpRequest<?> request = HttpRequest.POST("/", ProductStub.createProductRequest());
        HttpResponse<?> response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        verify(productsService).saveProduct(any(Product.class));
        clearInvocations(productsService);

        request = HttpRequest.POST("/", ProductStub.createProductRequest());
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        verify(productsService).saveProduct(any(Product.class));
    }

    @Test
    void testProductGetRequest() {
        when(productsService.getProducts()).then(invocation -> ProductStub.getProductsAnswer());

        HttpRequest<?> request = HttpRequest.GET("/");
        List<Product> response = client
                .toBlocking()
                .retrieve(request, Argument.of(List.class, Product.class));
        assertIterableEquals(ProductStub.getProductsAnswer(), response);
        verify(productsService).getProducts();
    }

    @Test
    void testProductGetByIdRequest() {
        when(productsService.getProductById(any(Integer.class)))
                .then(invocation -> ProductStub.getStoredProductAnswer());
        HttpRequest<?> request = HttpRequest.GET("/" + 1);
        Product response = client
                .toBlocking()
                .retrieve(request, Argument.of(Product.class));
        assertEquals(ProductStub.getStoredProductAnswer(), response);
        verify(productsService).getProductById(any(Integer.class));
    }

    @Test
    void testFindNonExistingProductReturns404() {
        when(productsService.getProductById(any(Integer.class)))
                .thenThrow(ProductNotFoundException.class);
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.GET("/1"))
        );

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @MockBean(ProductsService.class)
    ProductsService productsService() {
        return mock(ProductsService.class);
    }
}

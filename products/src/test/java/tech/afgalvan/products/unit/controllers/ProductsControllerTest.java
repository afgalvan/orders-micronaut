package tech.afgalvan.products.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsService;
import tech.afgalvan.products.shared.ProductsClient;
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
    ProductsClient client;

    @Inject
    ObjectMapper mapper;

    @Test
    void testProductPostRequest() {
        when(productsService.saveProduct(any(Product.class)))
            .then(invocation -> ProductStub.getStoredProductAnswer());
        HttpResponse<ProductResponse> response = client
            .saveProduct(ProductStub.createProductRequest());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        verify(productsService).saveProduct(any(Product.class));
        clearInvocations(productsService);

        response = client
            .saveProduct(ProductStub.createProductRequest());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        verify(productsService).saveProduct(any(Product.class));
    }

    @Test
    void testProductGetRequest() {
        when(productsService.getProducts()).then(invocation -> ProductStub.getProductsAnswer());

        List<Integer> response = client.getProducts()
            .stream()
            .map(ProductResponse::getId)
            .toList();
        assertIterableEquals(
            ProductStub.getAllProductsResponse(ProductResponse::getId),
            response
        );
        verify(productsService).getProducts();
    }

    @Test
    void testProductGetByIdRequest() {
        when(productsService.getProductById(any(Integer.class)))
            .then(invocation -> ProductStub.getStoredProductAnswer());
        HttpResponse<ProductResponse> response = client.getProductById(1);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(ProductStub.getStoredProductAnswer(), mapper.convertValue(response.body(), Product.class));
        verify(productsService).getProductById(any(Integer.class));
    }

    @Test
    void testFindNonExistingProductReturns404() {
        when(productsService.getProductById(any(Integer.class)))
            .thenThrow(ProductNotFoundException.class);
        HttpResponse<ProductResponse> response = client.getProductById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @MockBean(ProductsService.class)
    ProductsService productsService() {
        return mock(ProductsService.class);
    }
}

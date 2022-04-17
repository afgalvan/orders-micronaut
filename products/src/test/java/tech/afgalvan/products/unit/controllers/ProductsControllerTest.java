package tech.afgalvan.products.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsService;
import tech.afgalvan.products.shared.ProductsClient;
import tech.afgalvan.products.shared.RequestsArgumentsProvider;
import tech.afgalvan.products.shared.stubs.ProductStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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

    @ParameterizedTest
    @ArgumentsSource(RequestsArgumentsProvider.class)
    void testProductPostRequest(CreateProductRequest request) {
        when(productsService.saveProduct(any(Product.class)))
            .then(invocation -> ProductStub.getStoredProductAnswer());

        HttpResponse<ProductResponse> response = client.saveProduct(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        verify(productsService).saveProduct(any(Product.class));
    }

    @Test
    void testProductGetRequest() {
        when(productsService.getProducts())
            .then(invocation -> ProductStub.getProductsAnswer());

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testProductGetByIdRequest(int id) {
        when(productsService.getProductById(any(Integer.class)))
            .then(invocation -> ProductStub.getStoredProductAnswer());

        HttpResponse<ProductResponse> response = client.getProductById(id);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(ProductStub.getStoredProductAnswer(), mapper.convertValue(response.body(), Product.class));
        verify(productsService).getProductById(any(Integer.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testFindNonExistingProductReturns404(int id) {
        when(productsService.getProductById(any(Integer.class)))
            .thenThrow(ProductNotFoundException.class);

        HttpResponse<ProductResponse> response = client.getProductById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(productsService).getProductById(any(Integer.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testAnExistingProductIsDeleted(int id) {
        when(productsService.deleteProductById(any(Integer.class)))
            .thenReturn(true);

        HttpResponse<String> response = client.deleteProductById(id);
        assertEquals(HttpStatus.OK, response.getStatus());
        verify(productsService).deleteProductById(any(Integer.class));
    }

    @MockBean(ProductsService.class)
    ProductsService productsService() {
        return mock(ProductsService.class);
    }
}

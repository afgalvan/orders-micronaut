package tech.afgalvan.products.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.shared.ProductsClient;
import tech.afgalvan.products.shared.RequestsArgumentsProvider;
import tech.afgalvan.products.shared.stubs.ProductStub;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
class ProductsTest {
    private static final String[] products = ProductStub.PRODUCT_NAMES;

    @Inject
    ProductsClient client;

    @Inject
    ObjectMapper mapper;

    public Stream<ProductResponse> getAllProducts() {
        return client.getProducts().stream();
    }

    @ParameterizedTest
    @ArgumentsSource(RequestsArgumentsProvider.class)
    @Order(0)
    void whenISendAPOSTRequestToTheProductsEndpoint_thenTheProductShouldBeSaved(CreateProductRequest request) {
        HttpResponse<ProductResponse> response = client.saveProduct(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
    }

    @ParameterizedTest
    @ArgumentsSource(RequestsArgumentsProvider.class)
    @Order(1)
    void whenISendABadPOSTRequestToTheProductsEndpoint_thenA400ErrorShouldBeRetrieved(CreateProductRequest request) {
        request.setName(null);
        try {
            HttpResponse<?> response = client.saveProduct(request);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        } catch (HttpClientResponseException e) {
            // ignored
        }
    }

    @Test
    @Order(2)
    void whenISendAGETRequestToTheProductsEndpoint_thenAllRegisteredProductsShouldBeRetrieved() {
        List<String> response = getAllProducts()
                .map(ProductResponse::getName)
                .toList();
        assertIterableEquals(Arrays.asList(products), response);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @Order(3)
    void whenITryToFindAProductById_thenTheProductShouldBeRetrieved(int id) {
        ProductResponse response = client.getProductById(id).body();
        assertNotNull(response);
        assertEquals(products[id - 1], response.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8})
    @Order(4)
    void whenITryToFindAnNonExistingProduct_thenAn404ErrorShouldBeRetrieved(int id) {
        assertEquals(HttpStatus.NOT_FOUND, client.getProductById(id).getStatus());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @Order(5)
    void whenITryToDeleteAProductById_thenTheProductMustNotExist(int id) {
        HttpResponse<String> response = client.deleteProductById(id);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(HttpStatus.NOT_FOUND, client.getProductById(id).getStatus());
    }
}

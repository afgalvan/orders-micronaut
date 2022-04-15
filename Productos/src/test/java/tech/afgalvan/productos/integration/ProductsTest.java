package tech.afgalvan.productos.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.afgalvan.productos.controllers.requests.CreateProductRequest;
import tech.afgalvan.productos.controllers.responses.ProductResponse;
import tech.afgalvan.productos.integration.utils.HttpUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class ProductsTest {
    @Inject
    @Client("/api")
    HttpClient client;

    @Inject
    ObjectMapper mapper;

    private static boolean isAlreadyFilled;

    private static final String[] products = {"shampoo", "pencil", "soap", "paper"};

    @BeforeEach
    public void setUp() {
        HttpUtils.setClient(client);
    }

    private static void postProduct(String productName) {
        HttpUtils.POST("/products", productOf(productName));
    }

    private static void fillApi() {
        if (isAlreadyFilled) return;
        Stream.of(products).forEach(ProductsTest::postProduct);
    }

    private static CreateProductRequest productOf(String name) {
        return new CreateProductRequest(name, "https://github.com", 0);
    }

    public static Stream<Arguments> generateRequests() {
        return Stream.of(products)
                .map(ProductsTest::productOf)
                .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("generateRequests")
    void whenISendAPOSTRequestToTheProductsEndpoint_thenTheProductShouldBeSaved(CreateProductRequest request) {
        HttpResponse<ProductResponse> response = HttpUtils.POST("/products", request, ProductResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        isAlreadyFilled = true;
    }

    @Test
    void whenISendAGETRequestToTheProductsEndpoint_thenAllRegisteredProductsShouldBeRetrieved() {
        fillApi();
        @SuppressWarnings("unchecked")
        Class<List<ProductResponse>> cls = (Class<List<ProductResponse>>) (Object) List.class;
        List<String> response = HttpUtils.GET("/products", ProductResponse.class, cls)
                .stream()
                .map(ProductResponse::getName)
                .toList();
        assertIterableEquals(Arrays.asList(products), response);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void whenITryToFindAProductById_thenAllRegisteredProductsShouldBeRetrieved(int id) {
        fillApi();
        ProductResponse response = HttpUtils.GET("/products/" + id, ProductResponse.class);
        assertEquals(products[id - 1], response.getName());
    }
}

package tech.afgalvan.products.unit.stubs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.models.Product;

import java.util.List;
import java.util.function.Function;

public class ProductStub {
    public static final Product DEFAULT = new Product(
        "name",
        "https://img.jakpost.net/c/2017/05/22/2017_05_22_27382_1495457626._large.jpg",
        10,
        2000.0
    );
    private static final ObjectMapper mapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();

    public static Product getStoredProductAnswer() {
        return new Product(1, DEFAULT.getName(), DEFAULT.getImageUri(), 10, DEFAULT.getPrice());
    }

    public static List<Product> getProductsAnswer() {
        return List.of(getStoredProductAnswer());
    }


    public static List<ProductResponse> getAllProductsResponse() {
        return List.of(getProductResponse());
    }

    public static <R> List<R> getAllProductsResponse(Function<ProductResponse, R> function) {
        return getAllProductsResponse().stream().map(function).toList();
    }

    public static CreateProductRequest createProductRequest() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(DEFAULT, CreateProductRequest.class);
    }

    public static ProductResponse getProductResponse() {
        return mapper.convertValue(getStoredProductAnswer(), ProductResponse.class);
    }
}

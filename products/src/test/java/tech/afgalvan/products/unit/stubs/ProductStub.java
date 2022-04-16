package tech.afgalvan.products.unit.stubs;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import tech.afgalvan.products.models.Product;

import java.util.List;
import java.util.Map;

public class ProductStub {
    public static final Product DEFAULT = new Product("name", "https://img.jakpost.net/c/2017/05/22/2017_05_22_27382_1495457626._large.jpg", 2000.0);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Product getStoredProductAnswer() {
        return new Product(1, DEFAULT.getName(), DEFAULT.getImageUri(), DEFAULT.getPrice());
    }

    public static List<Product> getProductsAnswer() {
        return List.of(getStoredProductAnswer());
    }

    public static Map createProductRequest() {
        return mapper.convertValue(DEFAULT, Map.class);
    }
}

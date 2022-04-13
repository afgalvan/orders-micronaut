package tech.afgalvan.productos.stubs;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import tech.afgalvan.productos.controllers.commands.CreateProductCommand;
import tech.afgalvan.productos.models.Product;

import java.util.List;
import java.util.Map;

public class ProductStub {
    public static final Product DEFAULT = new Product("name", 2000);
    public static final CreateProductCommand CREATE_PRODUCT_COMMAND = new CreateProductCommand("name", 2000);

    public static Product getStoredProductAnswer() {
        return new Product(1, DEFAULT.getName(), DEFAULT.getPrice());
    }

    public static Iterable<Product> getProductsAnswer() {
        return List.of(getStoredProductAnswer());
    }

    public static Map createProductRequest() {
        return new ObjectMapper().convertValue(CREATE_PRODUCT_COMMAND, Map.class);
    }
}

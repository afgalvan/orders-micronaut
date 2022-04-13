package tech.afgalvan.productos.stubs;

import tech.afgalvan.productos.models.Product;

import java.util.List;

public class ProductStub {
    public static final Product DEFAULT = new Product("name", 2000);

    public static Product getStoredProductAnswer() {
        return new Product(1, DEFAULT.getName(), DEFAULT.getPrice());
    }

    public static Iterable<Product> getProductsAnswer() {
        return List.of(getStoredProductAnswer());
    }
}

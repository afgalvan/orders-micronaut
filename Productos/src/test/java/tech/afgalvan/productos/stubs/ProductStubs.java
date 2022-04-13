package tech.afgalvan.productos.stubs;

import tech.afgalvan.productos.models.Product;

public class ProductStubs {
    public static Product getStoredProductAnswer(Product product) {
        return new Product(1, product.getName(), product.getPrice());
    }
}

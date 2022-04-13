package tech.afgalvan.productos.controllers.requests;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CreateProductRequest {
    private String name;
    private int price;

    public CreateProductRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public CreateProductRequest() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

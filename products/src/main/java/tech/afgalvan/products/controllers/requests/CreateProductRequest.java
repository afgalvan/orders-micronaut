package tech.afgalvan.products.controllers.requests;

import io.micronaut.core.annotation.Introspected;

import java.net.URI;
import java.net.URISyntaxException;

@Introspected
public class CreateProductRequest {
    private String name;
    private URI imageUri;
    private double price;
    private String description;
    private int stock;

    public CreateProductRequest(String name, String imageUri, double price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.imageUri = URI.create(imageUri);
        this.description = description;
        this.stock = stock;
    }

    public CreateProductRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri.toString();
    }

    public void setImageUri(String imageUri) throws URISyntaxException {
        this.imageUri = new URI(imageUri);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

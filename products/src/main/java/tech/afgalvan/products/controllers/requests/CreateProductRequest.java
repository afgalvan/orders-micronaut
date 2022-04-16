package tech.afgalvan.products.controllers.requests;

import io.micronaut.core.annotation.Introspected;

import java.net.URI;
import java.net.URISyntaxException;

@Introspected
public class CreateProductRequest {
    private String name;
    private URI imageUri;
    private double price;

    public CreateProductRequest(String name, String imageUri, double price) {
        this.name = name;
        this.price = price;
        this.imageUri = URI.create(imageUri);
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
}

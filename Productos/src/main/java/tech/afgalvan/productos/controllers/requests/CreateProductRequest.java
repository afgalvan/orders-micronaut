package tech.afgalvan.productos.controllers.requests;

import io.micronaut.core.annotation.Introspected;

import java.net.URI;
import java.net.URISyntaxException;

@Introspected
public class CreateProductRequest {
    private String name;
    private URI imageUri;
    private double price;

    public CreateProductRequest(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public CreateProductRequest() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUri() {
        return imageUri.toString();
    }

    public void setImageUri(String imageUri) throws URISyntaxException {
        this.imageUri = new URI(imageUri);
    }
}

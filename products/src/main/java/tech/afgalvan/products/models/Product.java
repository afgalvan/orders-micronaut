package tech.afgalvan.products.models;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.Objects;

@MappedEntity
public class Product {
    private final String name;
    private final String imageUri;
    private final Double price;
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Integer id;

    public Product(String name, String imageUri, Double price) {
        this.name = name;
        this.imageUri = imageUri;
        this.price = price;
    }

    public Product(Integer id, String name, String imageUri, Double price) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId().equals(product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImageUri(), getPrice());
    }
}

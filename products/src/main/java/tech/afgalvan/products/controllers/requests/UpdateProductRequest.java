package tech.afgalvan.products.controllers.requests;

import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;


@Introspected
public class UpdateProductRequest extends CreateProductRequest {
    private Integer id;
    private final LocalDateTime updateDateTime = LocalDateTime.now();

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

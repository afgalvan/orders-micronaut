package tech.afgalvan.products.controllers.requests;

import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;


@Introspected
public class UpdateProductRequest extends CreateProductRequest {
    private final LocalDateTime updateDateTime = LocalDateTime.now();

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }
}

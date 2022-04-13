package tech.afgalvan.productos.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import tech.afgalvan.productos.services.ProductsService;

@Controller("/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Get(produces = MediaType.TEXT_HTML)
    public String hello() {
        return productsService.toString();
    }
}

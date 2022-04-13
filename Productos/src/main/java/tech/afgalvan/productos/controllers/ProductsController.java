package tech.afgalvan.productos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import tech.afgalvan.productos.controllers.commands.CreateProductCommand;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.services.ProductsService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Get
    public List<Product> hello() {
        return productsService.getProducts();
    }

    @Post
    public HttpResponse<Product> saveProduct(@Body @Valid CreateProductCommand command) {
        Product product = productsService.saveProduct(new Product(command.getName(), command.getPrice()));

        return HttpResponse
                .created(product)
                .headers(headers -> addLocationHeader(headers, product));
    }

    private void addLocationHeader(MutableHttpHeaders headers, Product product) {
        headers.location(location(product.getId()));
    }

    private URI location(Integer id) {
        return URI.create("/products" + id);
    }
}

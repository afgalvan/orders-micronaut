package tech.afgalvan.productos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tech.afgalvan.productos.controllers.requests.CreateProductRequest;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.services.ProductsService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/api/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Get
    public List<Product> getProducts() {
        return productsService.getProducts();
    }

    @ApiResponse(responseCode = "201", description = "Product successfully created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Post
    public HttpResponse<Product> saveProduct(@Body @Valid CreateProductRequest command) {
        Product product = productsService.saveProduct(new Product(command.getName(), command.getPrice()));

        return HttpResponse
                .created(product)
                .headers(headers -> addLocationHeader(headers, product));
    }

    private void addLocationHeader(MutableHttpHeaders headers, Product product) {
        headers.location(location(product.getId()));
    }

    private URI location(Integer id) {
        return URI.create("/products/" + id);
    }
}

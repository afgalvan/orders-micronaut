package tech.afgalvan.productos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tech.afgalvan.productos.controllers.requests.CreateProductRequest;
import tech.afgalvan.productos.controllers.responses.ProductResponse;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.services.ProductsService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/api/products")
public class ProductsController {
    private final ProductsService productsService;
    private final ObjectMapper mapper;

    public ProductsController(ProductsService productsService, ObjectMapper mapper) {
        this.productsService = productsService;
        this.mapper = mapper;
    }

    @Get
    public List<ProductResponse> getProducts() {
        return productsService.getProducts()
                .stream()
                .map(this::mapProductToResponse)
                .collect(Collectors.toList());
    }

    @ApiResponse(content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "201", description = "Product successfully created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Post
    public HttpResponse saveProduct(@Body @Valid CreateProductRequest command) {
        try {
            Product product = productsService.saveProduct(mapper.convertValue(command, Product.class));
            ProductResponse response = mapProductToResponse(product);

            return HttpResponse
                    .created(response)
                    .headers(headers -> addLocationHeader(headers, response));
        } catch (ConstraintViolationException | DataAccessException e) {
            return HttpResponse.badRequest("Error while saving the product");
        }
    }

    private void addLocationHeader(MutableHttpHeaders headers, ProductResponse product) {
        headers.location(location(product.getId()));
    }

    private URI location(Integer id) {
        return URI.create("/products/" + id);
    }

    private ProductResponse mapProductToResponse(Product product) {
        return mapper.convertValue(product, ProductResponse.class);
    }
}

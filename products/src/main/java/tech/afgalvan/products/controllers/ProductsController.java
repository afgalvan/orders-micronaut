package tech.afgalvan.products.controllers;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ErrorResponse;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/api/products")
public class ProductsController {
    private final ProductsService productsService;
    private final ObjectMapper mapper;
    private final Logger logger;

    public ProductsController(ProductsService productsService, ObjectMapper mapper) {
        this.productsService = productsService;
        this.mapper = mapper;
        this.logger = LoggerFactory.getLogger(ProductsController.class);
    }

    @Get
    public List<ProductResponse> getProducts() {
        return productsService.getProducts()
                .stream()
                .map(this::mapProductToResponse)
                .toList();
    }

    @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Get("/{id}")
    public HttpResponse getProductById(int id) {
        try {
            Product product = productsService.getProductById(id);
            return HttpResponse.ok(mapper.convertValue(product, ProductResponse.class));
        } catch (ProductNotFoundException e) {
            return HttpResponse.notFound(new ErrorResponse(e.getMessage()));
        }
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
            logger.error(e.getMessage());
            return HttpResponse.badRequest(new ErrorResponse(400, "Error while saving the product"));
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

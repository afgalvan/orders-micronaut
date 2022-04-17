package tech.afgalvan.products.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ErrorResponse;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.controllers.responses.Response;
import tech.afgalvan.products.models.Product;
import tech.afgalvan.products.models.exceptions.ProductNotFoundException;
import tech.afgalvan.products.services.ProductsService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
    public HttpResponse<Response> getProductById(int id) {
        try {
            Product product = productsService.getProductById(id);
            return HttpResponse.ok(mapProductToResponse(product));
        } catch (ProductNotFoundException e) {
            return HttpResponse.notFound(new ErrorResponse(e.getMessage()));
        }
    }

    @ApiResponse(responseCode = "201", description = "Product successfully created", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @Post
    public HttpResponse<Response> saveProduct(@Body @Valid CreateProductRequest request) {
        try {
            Product product = productsService
                .saveProduct(mapper.convertValue(request, Product.class));
            return HttpResponse.created(mapProductToResponse(product));
        } catch (DataAccessException | ConstraintViolationException e) {
            logger.error(e.getMessage());
            return HttpResponse.badRequest(new ErrorResponse(400, "Invalid input information"));
        }
    }

    @ApiResponse(responseCode = "200", description = "Product successfully deleted", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Delete("/{id}")
    public HttpResponse<String> deleteProductById(int id) {
        return productsService.deleteProductById(id)
            ? HttpResponse.ok("Product %d deleted".formatted(id))
            : HttpResponse.notFound("Product %d not found".formatted(id));
    }

    private ProductResponse mapProductToResponse(Product product) {
        return mapper.convertValue(product, ProductResponse.class);
    }
}

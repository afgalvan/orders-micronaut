package tech.afgalvan.products.shared;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.requests.UpdateProductRequest;
import tech.afgalvan.products.controllers.responses.ProductResponse;

import javax.validation.Valid;
import java.util.List;

@Client("/api/products")
public interface ProductsClient {
    @Post
    HttpResponse<ProductResponse> saveProduct(@Body @Valid CreateProductRequest request);

    @Get
    List<ProductResponse> getProducts();

    @Get("/{id}")
    HttpResponse<ProductResponse> getProductById(int id) throws HttpClientResponseException;

    @Put("/{id}")
    HttpResponse<String> updateProductById(int id, UpdateProductRequest request);

    @Delete("/{id}")
    HttpResponse<String> deleteProductById(int id);
}

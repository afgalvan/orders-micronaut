package tech.afgalvan.products.shared;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.shared.stubs.ProductStub;

import java.util.stream.Stream;

public class RequestsArgumentsProvider implements ArgumentsProvider {
    private static final String[] products = ProductStub.PRODUCT_NAMES;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return generateRequests(products);
    }

    public static Stream<Arguments> generateRequests(String[] products) {
        return Stream.of(products)
            .map(RequestsArgumentsProvider::productOf)
            .map(Arguments::of);
    }

    private static CreateProductRequest productOf(String name) {
        return new CreateProductRequest(name, "https://github.com", 0, "lit", 10);
    }
}

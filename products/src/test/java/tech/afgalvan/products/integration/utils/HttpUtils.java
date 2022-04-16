package tech.afgalvan.products.integration.utils;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;

import java.util.List;

public final class HttpUtils {
    private static HttpClient client;

    public static void setClient(HttpClient client) {
        HttpUtils.client = client;
    }

    public static <B, R> HttpResponse<R> POST(String endpoint, B body) {
        HttpRequest<B> request = HttpRequest.POST(endpoint, body);
        return client.toBlocking().exchange(request);
    }

    public static <B, R> HttpResponse<R> POST(String endpoint, B body, Class<R> cls) {
        HttpRequest<B> request = HttpRequest.POST(endpoint, body);
        return client.toBlocking().exchange(request, Argument.of(cls));
    }

    public static <R> R GET(String endpoint, Class<R> cls) {
        HttpRequest<?> request = HttpRequest.GET(endpoint);
        return client.toBlocking().retrieve(request, Argument.of(cls));
    }

    public static <R, I extends List<R>> List<R> GET(String endpoint, Class<R> cls, Class<I> iterable) {
        HttpRequest<?> request = HttpRequest.GET(endpoint);
        return client.toBlocking().retrieve(request, Argument.of(iterable, cls));
    }
}

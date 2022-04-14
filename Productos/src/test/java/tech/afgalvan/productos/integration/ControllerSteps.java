package tech.afgalvan.productos.integration;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.micronaut.context.ApplicationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.runtime.server.EmbeddedServer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerSteps {

    private static HttpClient client;
    private static ApplicationContext context;
    HttpRequest<?> request;
    HttpResponse<?> response;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        context = ApplicationContext.run();
        EmbeddedServer server = context.getBean(EmbeddedServer.class).start();
        client = context.createBean(HttpClient.class, server.getURL());
    }

    @When("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestToWithBody(String endpoint, String body) {
        sendPostRequestTo(endpoint, body);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int code) {
        assertEquals(code, response.code());
    }

    @And("^the response should be:$")
    public void theResponseShouldBe(String responseBody) throws IOException {
        var body = mapper.readValue(responseBody, Map.class);
        assertEquals(body, response.body());
    }

    private void sendPostRequestTo(String endpoint, String body) {
        request = HttpRequest.POST(endpoint, body);
        response = client.toBlocking().exchange(request, Argument.of(Map.class));
    }

    @AfterAll
    public static void tearDown() {
        client.stop();
        context.stop();
    }
}

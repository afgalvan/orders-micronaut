package tech.afgalvan.productos.integration;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerSteps {

    private static HttpClient client;
    HttpRequest<?> request;
    HttpResponse<?> response;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() throws MalformedURLException {
        client = HttpClient.create(new URL("http://localhost:9000"));
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
    }
}

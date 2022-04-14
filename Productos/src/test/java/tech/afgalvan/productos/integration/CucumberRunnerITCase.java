package tech.afgalvan.productos.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;
import tech.afgalvan.productos.integration.cucumber.MicronautObjectFactory;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/features"},
        glue = {"tech.afgalvan.productos.integration"},
        objectFactory = MicronautObjectFactory.class,
        features = "classpath:features",
        snippets = SnippetType.CAMELCASE)
public class CucumberRunnerITCase {
}
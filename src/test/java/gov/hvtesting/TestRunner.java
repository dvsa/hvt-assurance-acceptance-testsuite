package gov.hvtesting;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber-reports.html" },
    features = { "src/test/java/gov/hvtesting/Features/" },
    glue = { "gov.hvtesting" })
public class TestRunner {
}
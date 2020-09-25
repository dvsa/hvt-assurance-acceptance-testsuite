package gov.hvtesting;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions( plugin = {"pretty","html:target/html-report","json:target/cucumber.json"},
    features = {"src/test/java/gov/hvtesting/features/"})
public class TestRunner {
}

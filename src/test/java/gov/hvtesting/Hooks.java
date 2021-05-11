package gov.hvtesting;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.framework.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    TestContext testContext;
    DynamoDbApi dynamoDbApi;

    public Hooks(TestContext context) {
        testContext = context;
        //dynamoDbApi = new DynamoDbApi();
    }

    @Before("@UI")
    public void BeforeSteps() {
        // testContext.getWebDriverManager().createDriver();
    }

    @Before("@TestData")
    public void PrepareTestData() {
        dynamoDbApi = new DynamoDbApi();
        String atfId = dynamoDbApi.getAtfId();
        PropertyManager.getInstance().setAtfId(atfId);
    }

    @After("@UI")
    public void AfterSteps(Scenario scenario) {
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) testContext.getWebDriverManager().getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "png");
        }
        testContext.getWebDriverManager().closeDriver();
    }
}

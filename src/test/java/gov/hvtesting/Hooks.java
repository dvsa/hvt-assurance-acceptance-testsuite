package gov.hvtesting;

import gov.hvtesting.framework.TestDataService;
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
    TestDataService testDataService;

    public Hooks(TestContext context) {
        testContext = context;
        //dynamoDbApi = new DynamoDbApi();
        testDataService = new TestDataService();
    }

    @Before("@UI")
    public void BeforeSteps() throws Exception {
        // testContext.getWebDriverManager().createDriver();
        testDataService.setupTestData();
    }

    @Before("@TestData")
    public void PrepareTestData() {
        // dynamoDbApi = new DynamoDbApi();
        //String atfId = dynamoDbApi.getAtfId();
        //PropertyManager.getInstance(true).setAtfId(atfId);
    }

    @After("@UI")
    public void AfterSteps(Scenario scenario) throws Exception {
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) testContext.getWebDriverManager().getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "png");
        }
        testContext.getWebDriverManager().closeDriver();
        testDataService.tearDownTestData();
    }
}

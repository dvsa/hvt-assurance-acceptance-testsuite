package gov.hvtesting;

import gov.hvtesting.framework.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    TestContext testContext;

    public Hooks(TestContext context) {
        testContext = context;
    }

    @Before("@UI")
    public void BeforeSteps() {
        // testContext.getWebDriverManager().createDriver();
    }

    @After("@UI")
    public void AfterSteps() {
        testContext.getWebDriverManager().closeDriver();
    }
}

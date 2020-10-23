package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.EmailResentPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class EmailResentPageSteps {

    private EmailResentPage emailResentPage;
    private TestContext testContext;

    public EmailResentPageSteps(TestContext context) {
        testContext = context;
        emailResentPage = testContext.getPageObjectManager().getEmailResentPage();
    }

    @Then("I am on Email resent page")
    public void iAmOnEmailResentPage() throws Exception {
        emailResentPage.checkLinksAreVisible()
            .checkHeaderMessageVisible()
            .checkAvailabilityDates();
    }
}

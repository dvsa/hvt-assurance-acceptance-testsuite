package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ExpirationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExpirationPageSteps {

    private final static String ATF_ID = "070dedfe-84f2-440f-b485-e7a1113a389e";
    private ExpirationPage expirationPage;
    private TestContext testContext;
    private TokenGenerator tokenGenerator;

    public ExpirationPageSteps(TestContext context) {
        testContext = context;
        expirationPage = testContext.getPageObjectManager().getExpirationPage();
        tokenGenerator = new TokenGenerator();
    }

    @When("^I choose to (fully booked|some availability) link with expired link$")
    public void iChooseToSomeAvailabilityLinkWithExpiredLink(String availability) {
        Boolean isAvailable = availability.equals("some availability");
        String token = tokenGenerator.getToken(isAvailable, ATF_ID, true);
        expirationPage.navigateToExpirationPage(token);
    }

    @Then("I am on link expired page")
    public void iAmOnLinkExpiredPage() throws Exception {
        expirationPage.checkLinksAreVisible()
            .checkAvailabilityDates()
            .checkExpirationMessagesVisible();
    }

    @And("I ask for resending an email")
    public void iAskForResendingAnEmail() {
        expirationPage.clickResendEmailLink();
    }
}

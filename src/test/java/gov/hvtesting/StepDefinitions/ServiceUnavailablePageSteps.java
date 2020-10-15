package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ServiceUnavailablePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ServiceUnavailablePageSteps {

    private final static String ATF_ID = "070dedfe-84f2-440f-b485-e7a1113a389e";
    private final ServiceUnavailablePage serviceUnavailablePage;
    private final TestContext testContext;
    private final TokenGenerator tokenGenerator;

    public ServiceUnavailablePageSteps(TestContext context) {
        testContext = context;
        serviceUnavailablePage = testContext.getPageObjectManager().getServiceUnavailablePage();
        tokenGenerator = new TokenGenerator();
    }

    @Given("I choose to fully booked link with invalid token for a {string}")
    public void iChooseToFullyBookedLinkWithInvalidTokenForAReason(String reason) {
        String token = "";
        switch (reason) {
            case "incorrect_secret": {
                token = tokenGenerator.getToken(false, ATF_ID, false, "invalidSecret");
                break;
            }
            case "token_end_cut_out": {
                String tempToken = tokenGenerator.getToken(false, ATF_ID, false);
                token = tempToken.substring(0, tempToken.length() - 4);
                break;
            }
            case "token_missing_payload": {
                token = tokenGenerator.getTokenWithMissingPayload(false, ATF_ID, false);
                break;
            }
            case "non_existing_atf": {
                token = tokenGenerator.getToken(false, ATF_ID.replace("a", "b"), false);
                break;
            }
        }
        serviceUnavailablePage.navigateToServiceUnavailablePage(token);
    }

    @Then("I am on Service Unavailable page")
    public void iAmOnServiceUnavailablePage() {
        serviceUnavailablePage.checkHeader();
    }
}

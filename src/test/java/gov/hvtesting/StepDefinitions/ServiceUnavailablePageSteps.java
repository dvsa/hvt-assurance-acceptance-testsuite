package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ServiceUnavailablePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ServiceUnavailablePageSteps {

    private String ATF_ID;
    private final ServiceUnavailablePage serviceUnavailablePage;
    private final TestContext testContext;
    private final TokenGenerator tokenGenerator;
    private final DynamoDbApi dynamoDbApi;

    public ServiceUnavailablePageSteps(TestContext context) {
        testContext = context;
        serviceUnavailablePage = testContext.getPageObjectManager().getServiceUnavailablePage();
        tokenGenerator = new TokenGenerator();
        ATF_ID = PropertyManager.getInstance().getAtfId();
        dynamoDbApi = new DynamoDbApi();
    }

    @Given("I click the link to update the availability without out token")
    public void iChooseToFullyBookedLinkWithCutOutToken() throws Exception {
        tokenGenerator.generateToken(ATF_ID);
        String tempToken = dynamoDbApi.getToken(ATF_ID, false);
        String token = tempToken.substring(0, tempToken.length() - 4);
        serviceUnavailablePage.navigateToServiceUnavailablePage(token);
    }

    @Then("I am on Service Unavailable page")
    public void iAmOnServiceUnavailablePage() {
        serviceUnavailablePage.checkHeader();
    }
}

package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ConfirmationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ConfirmationPageSteps {

    private String ATF_ID;
    private ConfirmationPage confirmationPage;
    private TestContext testContext;
    private TokenGenerator tokenGenerator;
    private DynamoDbApi dynamoDbApi;

    public ConfirmationPageSteps(TestContext context) {
        testContext = context;
        confirmationPage = testContext.getPageObjectManager().getConfirmationPage();
        tokenGenerator = new TokenGenerator();
        ATF_ID = PropertyManager.getInstance(true).getAtfId();
        dynamoDbApi = new DynamoDbApi();
    }

    @And("^I choose to (fully booked|some availability) link$")
    public void iChooseToFullyBookedLink(String availability) throws Exception {
        Boolean isAvailable = availability.equals("some availability");
        tokenGenerator.generateToken(ATF_ID);
        String token = dynamoDbApi.getToken(ATF_ID, isAvailable);
        confirmationPage.navigateToConfirmationPage(token);
    }

    @Given("I choose {string} link for {string}")
    public void iChooseLinkFor(String availability, String atfName) throws Exception {
        Boolean isAvailable = availability.equals("some availability");
        String atfId = dynamoDbApi.getAtfId(atfName);
        tokenGenerator.generateToken(atfId);
        String token = dynamoDbApi.getToken(atfId, isAvailable);
        confirmationPage.navigateToConfirmationPage(token);
    }

    @Then("^I am on (Some Availability|Fully Booked)? confirmation page$")
    public void iAmOnConfirmationPage(String availabityType) throws Exception {
        confirmationPage.checkAvailabilityDates()
            .checkLinksAreVisible()
            .checkAvailabilityStatus(availabityType)
            .checkLastUpdatedOn();
    }
}

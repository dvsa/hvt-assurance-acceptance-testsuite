package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ConfirmationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static junit.framework.TestCase.assertTrue;

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

    @And("^I click the link to update the availability to (fully booked|yes we have availability)$")
    public void iChooseToFullyBookedLink(String availability) throws Exception {
        Boolean isAvailable = availability.equals("some availability");
        tokenGenerator.generateToken(ATF_ID);
        String token = dynamoDbApi.getToken(ATF_ID, isAvailable);
        confirmationPage.navigateToConfirmationPage(token);
    }

    @Given("I choose {string} link for {string}")
    public void iChooseLinkFor(String availability, String atfName) throws Exception {

        if (availability.equals("some availability")) {
            String atfId = dynamoDbApi.getAtfId(atfName);
            tokenGenerator.generateToken(atfId);
            String token = dynamoDbApi.getToken(atfId, true);
            confirmationPage.navigateToConfirmationPage(token);
            confirmationPage.selectYesOption();
            confirmationPage.submitMyAvailabilityButton();
        } else if (availability.equals("fully booked")){
            String atfId = dynamoDbApi.getAtfId(atfName);
            tokenGenerator.generateToken(atfId);
            String token = dynamoDbApi.getToken(atfId, false);
            confirmationPage.navigateToConfirmationPage(token);
            confirmationPage.selectNoOption();
            confirmationPage.submitMyAvailabilityButton();
        }
        else {
            String atfId = dynamoDbApi.getAtfId(atfName);
            tokenGenerator.generateToken(atfId);
            String token = dynamoDbApi.getToken(atfId, false);
            confirmationPage.navigateToConfirmationPage(token);
        }
    }

    @Then("^I am on (Some Availability|Fully Booked)? confirmation page$")
    public void iAmOnConfirmationPage(String availabilityType) throws Exception {
        confirmationPage.checkFullyBookedBannerIsVisible()
            .checkLinksAreVisible()
            .checkAvailabilityStatus(availabilityType)
            .checkLastUpdatedOn();
    }

    @Then("I check if I am on choose my availability page")
    public void iChooseMyAvailability(){
        confirmationPage.checkAvailabilityPageTitle();
    }

    @And("I select Yes radio button")
    public void iSelectTheYesRadioButton(){
        confirmationPage.selectYesOption();
    }

    @And("I select No radio button")
    public void iSelectTheNoRadioButton(){
        confirmationPage.selectNoOption();
    }

    @And("I submit my availability")
    public void submitMyAvailabilityButton(){
        confirmationPage.submitMyAvailabilityButton();
    }
}

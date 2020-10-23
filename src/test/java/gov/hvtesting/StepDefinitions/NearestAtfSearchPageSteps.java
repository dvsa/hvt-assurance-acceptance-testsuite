package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.NearestAtfSearchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NearestAtfSearchPageSteps {

    private NearestAtfSearchPage nearestAtfSearchPage;
    private TestContext testContext;

    public NearestAtfSearchPageSteps(TestContext context) {
        testContext = context;
        nearestAtfSearchPage = testContext.getPageObjectManager().getNearestAtfSearchPage();
    }

    @Given("I go to Nearest ATF Search page")
    public void iGoToNearestATFSearchPage() {
        nearestAtfSearchPage.navigateToSearchPage();
    }

    @When("I fill in my postcode with {string}")
    public void iFillInMyPostcodeWith(String geocode) {
        nearestAtfSearchPage.fillInPostcode(geocode);
        nearestAtfSearchPage.clickFindButton();
    }

    @Then("I can see error message")
    public void iCanSeeErrorMessage() {
        nearestAtfSearchPage.checkErrorMessageDisplayed();
    }

    @Then("I am on Nearest ATF Search page")
    public void iAmOnNearestATFSearchPage() {
        nearestAtfSearchPage.checkHeaderDisplayed();
    }
}

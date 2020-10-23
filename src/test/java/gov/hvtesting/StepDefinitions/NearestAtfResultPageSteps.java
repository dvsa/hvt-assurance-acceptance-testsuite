package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.NearestAtfResultsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NearestAtfResultPageSteps {

    private NearestAtfResultsPage nearestAtfResultsPage;
    private TestContext testContext;

    public NearestAtfResultPageSteps(TestContext context) {
        testContext = context;
        nearestAtfResultsPage = testContext.getPageObjectManager().getNearestAtfResultsPage();
    }

    @Then("I can see {int} nearest ATFs ordered")
    public void iCanSeeNearestATFsOrdered(int number) {
        //todo number will be applied with pagination
        //todo order to be checked
        nearestAtfResultsPage.checkResultsList();
        //todo check other info: tested at this centre...

    }

    @Then("I am on Nearest ATF Results page for {string}")
    public void iAmOnNearestATFResultsPageFor(String postcode) {
        nearestAtfResultsPage.checkHeader(postcode);
    }

    @When("I click back button")
    public void iClickBackButton() {
        nearestAtfResultsPage.clickBackButton();
    }
}

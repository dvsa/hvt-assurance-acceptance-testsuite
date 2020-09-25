package gov.hvtesting.features.StepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SampleSteps {

    @Given("^I am on main page$")
    public void i_am_on_the_main_page() throws Exception {
        //MainPageSample mainPageSample = new MainPageSample(driver);
        //mainPageSample.inputSearchText("test").clickSearchButton();
        System.out.println("first step");
    }

    @When("^I search text \"([^\"]*)\"$")
    public void iSearchText(String text) {
        System.out.println("2nd step: " + text);
    }

    @Then("^I am on result page$")
    public void iAmOnResultPage() {
        System.out.println("3rd step");
    }
}

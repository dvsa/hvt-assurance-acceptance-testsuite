package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.ApplicationCookies;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApplicationCookiesSteps {

    private ApplicationCookies applicationCookies;

    public ApplicationCookiesSteps(TestContext context) {
        applicationCookies = context.getPageObjectManager().getCheckApplicationCookies();
    }

    @And("I should be present with the cookie banner")
    public void iCheckTheCookieBanner() {
        applicationCookies.cookieBanner();
    }

    @Then("I should be see the cookie link in the footer")
     public void linksInTheFooter(){
        applicationCookies.checkCookieLinksIsVisible();
    }

    @When("I click the link, I should be taken to the {string} page")
    public void navigatedToCookiesPage(String pageTitle){
        applicationCookies.cookiePolicyLink();
        applicationCookies.cookiePage(pageTitle);
    }

    @And("The banner should contain the following cookie banner wording")
    public void iCheckTheCookieBannerText() {
        applicationCookies.cookieBannerText();
    }

    @Then("I click the Accept all cookies button")
    public void iClickAcceptAllCookiesButton() {
        applicationCookies.isAcceptAllCookiesButtonDisplayed();
    }

    @Then("I am presented with the success banner and I click Hide")
    public void cookieSuccessBannerIsDisplayed() {
        applicationCookies.cookieSuccessBannerIsDisplayed();
    }

    @And("I click Save changes button")
    public void iSaveCookiePreferenceButton() {
        applicationCookies.saveCookiePreferenceButton();
    }

    @And("I click the Set cookie preferences cookies button")
    public void iClickAcceptAllSetCookiesPreferenceButton() {
        applicationCookies.setCookiesPreferenceButton();
    }

    @Then("I select Off Google Analytics cookies")
    public void iTurnOffGoogleAnalytics() {
        applicationCookies.turnOffGARadioButton();
    }

    @Then("I check the {string} cookie is set")
    public void iCheckIfCookieIsSet(String cookieName) {
        applicationCookies.checkCookieSet(cookieName);
    }

    @Then("I check the {string} cookie is not set")
    public void iCheckIfCookieIsNotSet(String cookieName) {
        applicationCookies.checkCookieIsNotSet(cookieName);
    }

    @Then("I should be on Cookies for the {string} page")
    public void iConfirmIamOnTheCookiesPreferencePage(String pageTitle){
        applicationCookies.confirmIamOnTheCookiesPreferencePage(pageTitle);
    }
}

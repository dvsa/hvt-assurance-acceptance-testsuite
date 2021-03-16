package gov.hvtesting.StepDefinitions;

import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.ApplicationCookies;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ApplicationCookiesSteps {

    private ApplicationCookies applicationCookies;

    public ApplicationCookiesSteps(TestContext context) {
        applicationCookies = context.getPageObjectManager().getCheckApplicationCookies();
    }

    @Then("I should be present with the cookie banner")
    public void iCheckTheCookieBanner() {
        applicationCookies.cookieBanner();
    }

    @Then("I check if the cookie link is in the footer")
     public void linksInTheFooter(){
        applicationCookies.checkCookieLinksIsVisible();
    }

    @And("The banner should contain the following cookie banner wording")
    public void iCheckTheCookieBannerText() {
        applicationCookies.cookieBannerText();
    }

    @And("I click the Accept all cookies button")
    public void iClickAcceptAllCookiesButton() {
        applicationCookies.acceptAllCookiesButton();
    }

    @Then("I am presented with the success banner and I click Hide")
    public void cookieSuccessBannerIsDisplayed() {
        applicationCookies.cookieSuccessBannerIsDisplayed();
    }

    @And("I check if the banner is hidden")
    public void cookieSuccessBannerIsHidden() {
        applicationCookies.cookieSuccessBannerIsHidden();
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

package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.PropertyManager;

public class NearestAtfSearchPage extends BasePage {

    private static final String errorMessageText = "Enter a real postcode";
    private static final String headerText = "Find a test centre for an HGV, bus or trailer MOT";
    private String postcodeFieldId = "postcode";
    private String findButtonId = "//button[@type='submit']";
    private String errorMessageId = "postcode-error";
    private String headerClass = "govuk-heading-xl";
    protected RemoteWebDriver driver;

    public NearestAtfSearchPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void navigateToSearchPage() {
        String url = PropertyManager.getInstance().getSearchNearestAtfUrl();
        driver.get(url);
    }

    public void fillInPostcode(String geocode) {
        inputText(By.id(postcodeFieldId), geocode);
    }

    public void clickFindButton() {
        clickElement(By.xpath(findButtonId));
    }

    public void checkErrorMessageDisplayed(){
        String actualErrorMessageText = getElementText(By.id(errorMessageId));
        assertThat(actualErrorMessageText, containsString(errorMessageText));
    }

    public void checkHeaderDisplayed() {
        String actualHeaderText = getElementText(By.className(headerClass));
        assertThat(actualHeaderText, containsString(headerText));
    }
}

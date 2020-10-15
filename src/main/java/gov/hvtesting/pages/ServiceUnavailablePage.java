package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.PropertyManager;

public class ServiceUnavailablePage extends BasePage {

    private static final String serviceUnavailableHeader = "Sorry, there is a problem with the service";
    private String headerId = "unavailable-header";
    protected RemoteWebDriver driver;

    public ServiceUnavailablePage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void navigateToServiceUnavailablePage(String token) {
        String url = PropertyManager.getInstance(true).getUpdateAvailabilityUrl();
        driver.get(url + token);
    }


    public ServiceUnavailablePage checkHeader(){
        String headerText = getElementText(By.id(headerId));
        assertThat(serviceUnavailableHeader, is(equalTo(headerText)));
        return this;
    }
}

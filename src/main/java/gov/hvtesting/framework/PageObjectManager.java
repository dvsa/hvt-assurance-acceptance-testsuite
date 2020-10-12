package gov.hvtesting.framework;

import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.pages.ConfirmationPage;

public class PageObjectManager {

    private RemoteWebDriver driver;
    private ConfirmationPage confirmationPage;

    public PageObjectManager(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public ConfirmationPage getConfirmationPage(){
        return (confirmationPage == null) ? confirmationPage = new ConfirmationPage(driver) : confirmationPage;
    }
}

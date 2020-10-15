package gov.hvtesting.framework;

import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.pages.ConfirmationPage;
import gov.hvtesting.pages.EmailResentPage;
import gov.hvtesting.pages.ExpirationPage;
import gov.hvtesting.pages.ServiceUnavailablePage;

public class PageObjectManager {

    private RemoteWebDriver driver;
    private ConfirmationPage confirmationPage;
    private ExpirationPage expirationPage;
    private EmailResentPage emailResentPage;
    private ServiceUnavailablePage serviceUnavailablePage;

    public PageObjectManager(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public ConfirmationPage getConfirmationPage(){
        return (confirmationPage == null) ? confirmationPage = new ConfirmationPage(driver) : confirmationPage;
    }

    public ExpirationPage getExpirationPage(){
        return (expirationPage == null) ? expirationPage = new ExpirationPage(driver) : expirationPage;
    }

    public ServiceUnavailablePage getServiceUnavailablePage(){
        return (serviceUnavailablePage == null) ? serviceUnavailablePage = new ServiceUnavailablePage(driver) : serviceUnavailablePage;
    }

    public EmailResentPage getEmailResentPage(){
        return (emailResentPage == null) ? emailResentPage = new EmailResentPage(driver) : emailResentPage;
    }
}

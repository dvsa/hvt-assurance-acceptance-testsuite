package gov.hvtesting.framework;

import gov.hvtesting.pages.*;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PageObjectManager {

    private RemoteWebDriver driver;
    private ConfirmationPage confirmationPage;
    private EmailResentPage emailResentPage;
    private ServiceUnavailablePage serviceUnavailablePage;
    private NearestAtfResultsPage nearestAtfResultsPage;
    private NearestAtfSearchPage nearestAtfSearchPage;
    private ApplicationCookies applicationCookies;

    public PageObjectManager(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public ConfirmationPage getConfirmationPage(){
        return (confirmationPage == null) ? confirmationPage = new ConfirmationPage(driver) : confirmationPage;
    }

    public ServiceUnavailablePage getServiceUnavailablePage(){
        return (serviceUnavailablePage == null) ? serviceUnavailablePage = new ServiceUnavailablePage(driver) : serviceUnavailablePage;
    }

    public EmailResentPage getEmailResentPage(){
        return (emailResentPage == null) ? emailResentPage = new EmailResentPage(driver) : emailResentPage;
    }

    public NearestAtfSearchPage getNearestAtfSearchPage(){
        return (nearestAtfSearchPage == null) ? nearestAtfSearchPage = new NearestAtfSearchPage(driver) : nearestAtfSearchPage;
    }

    public NearestAtfResultsPage getNearestAtfResultsPage(){
        return (nearestAtfResultsPage == null) ? nearestAtfResultsPage = new NearestAtfResultsPage(driver) : nearestAtfResultsPage;
    }

    public ApplicationCookies getCheckApplicationCookies(){
        return (applicationCookies == null) ? applicationCookies = new ApplicationCookies(driver) : applicationCookies;
    }
}

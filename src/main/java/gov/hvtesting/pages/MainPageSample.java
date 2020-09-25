package gov.hvtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.PageFactory;

public class MainPageSample extends BasePage {

    public static final String PAGE_TITLE = "Sample page's title";

    protected RemoteWebDriver driver;
    protected String searchFieldLocator = "//input[@name='q']";
    protected String searchButtonLocator = "//input[@name='btnK']";

    public MainPageSample(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean isLoaded() {
        return true;
    }

    public ResultPageSample clickSearchButton() throws Exception {
        clickElement(By.xpath(searchButtonLocator));
        return PageFactory.newPage(driver, ResultPageSample.class);
    }

    public MainPageSample inputSearchText(String searchText) throws Exception {
        inputText(By.xpath(searchFieldLocator), searchText);
        return this;
    }
}

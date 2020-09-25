package gov.hvtesting.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public class ResultPageSample extends BasePage {

    protected RemoteWebDriver driver;

    public ResultPageSample(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean isLoaded() {
        return true;
    }
}

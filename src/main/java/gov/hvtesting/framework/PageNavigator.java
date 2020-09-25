package gov.hvtesting.framework;

import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.pages.BasePage;
import gov.hvtesting.pages.MainPageSample;

public class PageNavigator {

    private MainPageSample mainPageSample;

    public <T extends BasePage> T navigateToPage(RemoteWebDriver driver, String path, Class<T> clazz) throws Exception {
        driver.get(path);
        return PageFactory.newPage(driver, clazz);
    }
}

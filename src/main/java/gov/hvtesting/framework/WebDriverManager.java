package gov.hvtesting.framework;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverManager {

    private static final String LOCAL_CHROME_DRIVER_FILE_PATH = "src/test/resources/driver/chromedriver";
    private RemoteWebDriver driver = null;
    private DesiredCapabilities dr = null;

    public RemoteWebDriver createDriver() {
        String baseUrl = "";
        baseUrl = PropertyManager.getInstance().getURL();
        System.setProperty("webdriver.chrome.driver", LOCAL_CHROME_DRIVER_FILE_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }

    public RemoteWebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }
}
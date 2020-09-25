package gov.hvtesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public abstract class BaseTest {

    private static final String LOCAL_CHROME_DRIVER_FILE_PATH = "src/test/resources/driver/chromedriver";
    private static final String PROPERTIES_FILE_PATH = "src/test/resources/test.properties";
    private static Properties props = new Properties();
    protected RemoteWebDriver driver = null;
    private DesiredCapabilities dr = null;

    @Before
    public void setupBaseTest() {

        if (isLocal().equals("true")) {
            loadPropertiesFromFile(PROPERTIES_FILE_PATH, false);
            System.setProperty("webdriver.chrome.driver", LOCAL_CHROME_DRIVER_FILE_PATH);
            driver = new ChromeDriver();
        } else {
            //todo grid setup
        }
        driver.manage().window().maximize();
        String a = baseUrl();
        driver.get(a);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            if (null != driver) {
                driver.manage().deleteAllCookies();
            }
            driver.close();
        } else if (null != driver) {
            driver.quit();
        }
        driver = null;
    }

    private String isLocal() {
        return getProperty("test.local");
    }

    private String baseUrl() {
        return getProperty("test.baseUrl");
    }

    protected String getProperty(String key) {
        loadPropertiesFromFile(PROPERTIES_FILE_PATH, false);
        String s = props.getProperty(key);
        return (s != null) ? s.trim() : null;
    }

    private void loadPropertiesFromFile(String defaultPropertiesFilePath, boolean ignoreNotFound) {
        String path = defaultPropertiesFilePath;

        try {
            InputStream propsStream;
            propsStream = new FileInputStream(new File(path).getPath());
            props.load(propsStream);
        } catch (Exception ex) {
            if (!ignoreNotFound) {
                ex.printStackTrace();
                throw new RuntimeException(
                    "Problem loading test properties file [" + ex.getMessage() + "]. Is " + defaultPropertiesFilePath + " on the " +
                        "classpath?", ex);
            }
        }
    }
}
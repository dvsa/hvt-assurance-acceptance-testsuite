package gov.hvtesting.framework;

import java.lang.reflect.Constructor;

import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.pages.BasePage;

public class PageFactory {
    public static <T extends BasePage> T newPage(RemoteWebDriver driver, Class<T> clazz) throws Exception {
        return getNewPageInstance(driver, clazz);
    }

    private static <T> T getNewPageInstance(RemoteWebDriver driver, Class<T> clazz) throws Exception {
        try {
            Constructor<T> constructor = clazz.getConstructor(RemoteWebDriver.class);
            constructor.setAccessible(true);
            return constructor.newInstance(driver);
        } catch (Exception e) {
            throw new Exception(e.getCause().toString(), e);
        }
    }
}

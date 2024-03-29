package gov.hvtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public abstract class BasePage {

    protected RemoteWebDriver driver;
    protected WebDriverWait wait;

    public BasePage(final RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 30);
    }

    protected void inputText(By selector, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(selector));
        try {
            driver.findElement(selector).sendKeys(text);
        } catch (Exception ex) {
            driver.navigate().refresh();
            driver.findElement(selector).sendKeys(text);
        }
    }

    protected void clickElement(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        try {
            driver.findElement(selector).click();
        } catch (Exception ex) {
            driver.navigate().refresh();
            clickElement(selector);
        }
    }

    protected String getElementText(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        try {
            return driver.findElement(selector).getText();
        } catch (Exception ex) {
            driver.navigate().refresh();
            return getElementText(selector);
        }
    }

    protected WebElement getElement(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        try {
            return driver.findElement(selector);
        } catch (Exception ex) {
            driver.navigate().refresh();
            return getElement(selector);
        }
    }

    protected List<WebElement> getElements(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        try {
            return driver.findElements(selector);
        } catch (Exception ex) {
            driver.navigate().refresh();
            return getElements(selector);
        }
    }
}

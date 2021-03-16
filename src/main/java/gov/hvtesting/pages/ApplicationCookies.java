package gov.hvtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ApplicationCookies extends BasePage {

    protected RemoteWebDriver driver;
    private String cookieHeaderId = "cm_cookie_notification";
    private String pageTitleClass = "govuk-heading-xl";
    private String cookiesLinkXpath = "/html/body/footer/div/div/div[1]/ul/li[3]/a";

    public ApplicationCookies(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void checkCookieLinksIsVisible() {
        WebElement cookiesLink = driver.findElement(By.xpath(cookiesLinkXpath));

        boolean cookieFooterLink =  cookiesLink.isDisplayed();
        assertThat(cookieFooterLink, is(true));
    }

    public void cookieBanner() {
        boolean cookieBanner = driver.findElement(By.id(cookieHeaderId)).isDisplayed();
        assertThat(cookieBanner, is(true));
    }


    public void cookieBannerText() {
        String cookieHeaderContentClass = "govuk-cookie-banner__content";
        String cookieHeaderTitle = "Cookies on Find a test centre for an HGV, bus or trailer MOT";
        String cookieHeaderText = "We use some essential cookies to make this service work.";

        String actualContentTitle = getElementText(By.id(cookieHeaderId));
        String actualContentText = getElementText(By.className(cookieHeaderContentClass));

        assertThat(actualContentTitle, containsString(cookieHeaderTitle));
        assertThat(actualContentText, containsString(cookieHeaderText));
    }

    public void isAcceptAllCookiesButtonDisplayed() {

        String acceptAllCookiesButtonXpath = "//*[@id=\"cm_cookie_notification\"]/div/div/div[2]/button";
        boolean acceptAllCookiesButton = driver.findElement(By.xpath(acceptAllCookiesButtonXpath)).isDisplayed();

        assertThat(acceptAllCookiesButton, is(true));
        clickElement(By.xpath(acceptAllCookiesButtonXpath));

    }

    public void cookieSuccessBannerIsDisplayed() {
        boolean acceptAllCookiesSuccess = driver.findElement(By.id("accept-all-cookies-success")).isDisplayed();
        boolean hideLink = driver.findElement(By.id("cookie-accept-all-success-banner-hide")).isDisplayed();

        if (acceptAllCookiesSuccess && hideLink)
        {
            WebElement clickHideLinkOnSuccessBanner = driver.findElement(By.id("cookie-accept-all-success-banner-hide"));
            clickHideLinkOnSuccessBanner.click();
        } else {
            driver.close();
        }
    }

    public void saveCookiePreferenceButton() {
        String saveCookiePreferenceButton = "save-cookie-preferences";
        clickElement(By.id(saveCookiePreferenceButton));
    }

    public void setCookiesPreferenceButton() {
        String setCookiesPreferenceButtonXpath = "//*[@id=\"cm_cookie_notification\"]/div/div/div[2]/a";
        boolean cookiesPreferenceButton = driver.findElement(By.xpath(setCookiesPreferenceButtonXpath)).isEnabled();

        assertThat(cookiesPreferenceButton, is(true));
        clickElement(By.xpath(setCookiesPreferenceButtonXpath));
    }

    public void turnOffGARadioButton(){
        WebElement turnOffGARadioButton = driver.findElement(By.id("radio-analytics-off"));
        turnOffGARadioButton.click();
    }

    public void checkCookieSet(String cookieName) {
        assertThat("Cookie " + cookieName + " should have been set", isCookieSet(cookieName), is(true));
    }

    public void checkCookieIsNotSet(String cookieName) {
        assertThat("Cookie " + cookieName + " should have been set", isCookieSet(cookieName), is(false));
    }

    private boolean isCookieSet(String name) {
        return driver.manage().getCookies().stream()
                .anyMatch((Cookie c) -> c.getName().contains(name));
    }

    public ApplicationCookies confirmIamOnTheCookiesPreferencePage(String pageTitle) {
        String pageTitleText = pageTitle;
        String actualPageTitle = getElementText(By.className(pageTitleClass));
        assertThat(actualPageTitle, containsString(pageTitleText));
        return this;
    }

    public void cookiePolicyLink(){
        WebElement cookiesLink = driver.findElement(By.xpath(cookiesLinkXpath));
        cookiesLink.click();
    }

    public void cookiePage(String pageTitle){
        String cookiePageHeading = getElementText(By.xpath("//*[@id=\"main-content\"]/div[1]/div/h2[1]"));
        assertThat(cookiePageHeading, containsString(pageTitle));

    }
}

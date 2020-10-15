package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import org.exparity.hamcrest.date.DateMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.utils.DateUtil;

public class ExpirationPage extends BasePage {

    private static final String expirationPageMessage = "Sorry, we cannot update your availability";
    private static final String linkExpiredMessage = "The link you used has expired.";
    protected RemoteWebDriver driver;
    protected String expirationPageMessageId = "expired-header";
    protected String linkExpiredMessageClass = "govuk-inset-text";
    protected String resendEmailLinkText = "ask us to resend the email";
    protected String feedbackLinkText = "What did you think of this service?";
    protected String availabilityStartDateId = "start-date";
    protected String availabilityEndDateId = "end-date";
    private DateUtil dateUtil;

    public ExpirationPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        dateUtil = new DateUtil();
    }

    public void navigateToExpirationPage(String token) {
        String url = PropertyManager.getInstance(true).getUpdateAvailabilityUrl();
        driver.get(url + token);
    }


    public ExpirationPage checkLinksAreVisible() {
        WebElement resendEmailLink = getElement(By.linkText(resendEmailLinkText));
        assertThat(resendEmailLink.isDisplayed(), is(true));

        WebElement feedbackLink = getElement(By.linkText(feedbackLinkText));
        assertThat(feedbackLink.isDisplayed(), is(true));
        return this;
    }

    public ExpirationPage checkExpirationMessagesVisible() {
        String  expirationPageMessageText = getElementText(By.id(expirationPageMessageId));
        assertThat(expirationPageMessageText, is(equalTo(expirationPageMessage)));

        String linkExpiredMessageText = getElementText(By.className(linkExpiredMessageClass));
        assertThat(linkExpiredMessageText, is(equalTo(linkExpiredMessage)));
        return this;
    }


    public ExpirationPage checkAvailabilityDates() throws Exception {
        String startDateText = getElementText(By.id(availabilityStartDateId));
        String endDateText = getElementText(By.id(availabilityEndDateId));
        Date startDate =  new SimpleDateFormat("d MMMM yyyy").parse(startDateText);
        Date endDate = new SimpleDateFormat("d MMMM yyyy").parse(endDateText);

        Date expectedStartDate = Date.from(dateUtil.getLastMonday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expectedEndDate = Date.from(dateUtil.getLastMonday().plusDays(27).atStartOfDay(ZoneId.systemDefault()).toInstant());

        MatcherAssert.assertThat(startDate, DateMatchers.sameDay(expectedStartDate));
        MatcherAssert.assertThat(endDate, DateMatchers.sameDay(expectedEndDate));
        return this;
    }

    public EmailResentPage clickResendEmailLink(){
       clickElement(By.linkText(resendEmailLinkText));
        return new EmailResentPage(driver);
    }
}

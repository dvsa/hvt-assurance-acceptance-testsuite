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

import gov.hvtesting.utils.DateUtil;

public class EmailResentPage extends BasePage {

    private static final String resendEmailHeader = "We have sent you an email";
    private static final Integer reportingPeriodLength = 27;
    protected RemoteWebDriver driver;
    protected String resentEmailHeaderClassName = "govuk-panel__title";
    protected String feedbackLinkText = "What did you think of this service?";
    protected String availabilityStartDateId = "start-date";
    protected String availabilityEndDateId = "end-date";
    private DateUtil dateUtil;


    public EmailResentPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        dateUtil = new DateUtil();
    }

    public EmailResentPage checkLinksAreVisible() {
        WebElement feedbackLink = getElement(By.linkText(feedbackLinkText));
        assertThat(feedbackLink.isDisplayed(), is(true));
        return this;
    }

    public EmailResentPage checkHeaderMessageVisible() {
        String resendEmailHeaderText = getElementText(By.className(resentEmailHeaderClassName));
        assertThat(resendEmailHeaderText, is(equalTo(resendEmailHeader)));
        return this;
    }
    
    public EmailResentPage checkAvailabilityDates() throws Exception {
        String startDateText = getElementText(By.id(availabilityStartDateId));
        String endDateText = getElementText(By.id(availabilityEndDateId));
        Date startDate =  new SimpleDateFormat("d MMMM yyyy").parse(startDateText);
        Date endDate = new SimpleDateFormat("d MMMM yyyy").parse(endDateText);

        Date expectedStartDate = Date.from(dateUtil.getNextMonday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expectedEndDate = Date.from(dateUtil.getNextMonday().plusDays(reportingPeriodLength).atStartOfDay(ZoneId.systemDefault()).toInstant());

        MatcherAssert.assertThat(startDate, DateMatchers.sameDay(expectedStartDate));
        MatcherAssert.assertThat(endDate, DateMatchers.sameDay(expectedEndDate));
        return this;
    }

}

package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.exparity.hamcrest.date.DateMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.utils.DateUtil;

public class ConfirmationPage extends BasePage {

    private static final String fullyBookedText = "is fully booked";
    private static final String someAvailabilityText = "can take more MOT bookings";
    protected RemoteWebDriver driver;
    protected String availabilityDatesId = "availability-dates";
    protected String availabilityPanelId = "availability-panel";
    protected String lastUpdatedOnId = "last-updated-date";
    protected String feedbackLinkText = "What did you think of this service?";
    private DateUtil dateUtil;

    public ConfirmationPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        dateUtil = new DateUtil();
    }

    public void navigateToConfirmationPage(String token) {
        String url = PropertyManager.getInstance(true).getUpdateAvailabilityUrl();
        driver.get(url + token);
    }

    public ConfirmationPage checkAvailabilityStatus(String availabilityType) throws Exception {
        String availabilityStatusText = getElementText(By.id(availabilityPanelId));
        if (availabilityType.equals("Fully Booked")) {
            assertThat(availabilityStatusText, endsWith(fullyBookedText));
        } else if (availabilityType.equals("Some Availability")) {
            assertThat(availabilityStatusText, endsWith(someAvailabilityText));
        } else {
            throw new Exception("Incorrect Availability type provided: " + availabilityType);
        }
        return this;
    }

    public ConfirmationPage checkLastUpdatedOn() throws ParseException {
        String lastUpdatedOn = getElementText(By.id(lastUpdatedOnId));
        Date lastUpdatedDate = dateUtil.extractDate(lastUpdatedOn.replace(" at", ""), "(\\d{1,2} \\w+ \\d{4} \\d{1,2}:\\d{2}\\wm)", "d MMMM yyyy hh:mma");
        Date expectedTimeNow = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        assertThat(lastUpdatedDate, DateMatchers.within(1, ChronoUnit.MINUTES, expectedTimeNow));
        return this;
    }

    public ConfirmationPage checkLinksAreVisible() {
        WebElement feedbackLink = getElement(By.linkText(feedbackLinkText));
        assertThat(feedbackLink.isDisplayed(), is(true));
        return this;
    }

    public ConfirmationPage checkAvailabilityDates() throws Exception {
        String datesText = getElementText(By.id(availabilityDatesId));
        Date startDate = extractStartDate(datesText);
        Date endDate = extractEndDate(datesText);

        Date expectedStartDate = Date.from(dateUtil.getLastMonday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expectedEndDate = Date.from(dateUtil.getLastMonday().plusDays(27).atStartOfDay(ZoneId.systemDefault()).toInstant());

        MatcherAssert.assertThat(startDate, DateMatchers.sameDay(expectedStartDate));
        MatcherAssert.assertThat(endDate, DateMatchers.sameDay(expectedEndDate));
        return this;
    }

    public String getAtfName(){
        return getElementText(By.id(availabilityPanelId))
            .replace(someAvailabilityText, "")
            .replace(fullyBookedText, "")
            .trim();
    }

    private Date extractStartDate(String input) throws ParseException {
        String regex = "(\\d{1,2} \\w+ \\d{4})(?: and )";
        return dateUtil.extractDate(input, regex, "d MMMM yyyy");
    }

    private Date extractEndDate(String input) throws ParseException {
        String regex = "(?:and )(\\d{1,2} \\w+ \\d{4})";
        return dateUtil.extractDate(input, regex, "d MMMM yyyy");
    }
}

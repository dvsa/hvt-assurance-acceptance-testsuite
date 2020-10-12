package gov.hvtesting.StepDefinitions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.NotImplementedException;
import org.exparity.hamcrest.date.DateMatchers;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.framework.TokenGenerator;
import gov.hvtesting.pages.ConfirmationPage;
import gov.hvtesting.utils.DateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ConfirmationPageSteps {

    private final static String ATF_ID = "59c31a59-b03d-42d8-bcde-b365be4b28d4";
    private ConfirmationPage confirmationPage;
    private TestContext testContext;
    private DateUtil dateUtil;
    private TokenGenerator tokenGenerator;

    public ConfirmationPageSteps(TestContext context) {
        testContext = context;
        confirmationPage = testContext.getPageObjectManager().getConfirmationPage();
        dateUtil = new DateUtil();
        tokenGenerator = new TokenGenerator();
    }

    @And("^I choose to (fully booked|some availability) link$")
    public void iChooseToFullyBookedLink(String availability) {
        Boolean isAvailable = availability.equals("some availability");
        String token = tokenGenerator.getToken(isAvailable, ATF_ID, false);
        confirmationPage.navigateToConfirmationPage(token);
    }

    @When("^I choose to (fully booked|some availability) link with expired link$")
    public void iChooseToSomeAvailabilityLinkWithExpiredLink(String availability) {
        Boolean isAvailable = availability.equals("some availability");
        String token = tokenGenerator.getToken(isAvailable, ATF_ID, true);
        confirmationPage.navigateToConfirmationPage(token);
    }

    @Then("^ATF availability is set to (some availability|fully booked)$")
    public void atfAvailabilityIsSetToSomeAvailability(String availabilityType) throws ParseException {
        DynamoDbApi dynamoDbApi = new DynamoDbApi();
        Response response = dynamoDbApi.getAtfAvailabilityData(ATF_ID);
        Date expectedStartDate = Date.from(dateUtil.getLastMonday().atStartOfDay(ZoneId.systemDefault()).plusHours(8).toInstant());
        Date expectedEndDate =
            Date.from(dateUtil.getLastMonday().plusDays(27).atStartOfDay(ZoneId.systemDefault()).plusHours(17).toInstant());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String expectedStartDateFormatted = dateFormat.format(expectedStartDate);
        String expectedEndDateFormatted = dateFormat.format(expectedEndDate);

        Boolean availabilityStatus = availabilityType.equals("some availability");
        response.then().body("availability.isAvailable", equalTo(availabilityStatus));
        response.then().body("availability.startDate", equalTo(expectedStartDateFormatted));
        response.then().body("availability.endDate", equalTo(expectedEndDateFormatted));

        String lastUpdatedOn = response.jsonPath().get("availability.lastUpdated");
        Date lastUpdatedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(lastUpdatedOn);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date expectedLastUpdatedOn = localDateFormat.parse(simpleDateFormat.format(new Date()));
        assertThat(lastUpdatedDate, DateMatchers.within(2, ChronoUnit.MINUTES, expectedLastUpdatedOn));
    }

    @And("wait some time")
    public void waitSomeTime() {
    }

    @Then("I get link expired message")
    public void iGetLinkExpiredMessage() {
        throw new NotImplementedException("Implement step for expired link");
    }

    @Then("^I am on (Some Availability|Fully Booked)? confirmation page$")
    public void iAmOnConfirmationPage(String availabityType) throws Exception {
        confirmationPage.checkAvailabilityDates()
            .checkLinksAreVisible()
            .checkAvailabilityStatus(availabityType)
            .checkLastUpdatedOn();
    }
}

package gov.hvtesting.StepDefinitions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.exparity.hamcrest.date.DateMatchers;

import gov.hvtesting.framework.DynamoDbApi;
import gov.hvtesting.framework.PropertyManager;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.ConfirmationPage;
import gov.hvtesting.utils.DateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class DynamoDbSteps {

    private static final Integer reportingPeriodLength = 28;
    private String ATF_ID;
    private ConfirmationPage confirmationPage;
    private TestContext testContext;
    private DateUtil dateUtil;

    public DynamoDbSteps(TestContext context) {
        testContext = context;
        confirmationPage = testContext.getPageObjectManager().getConfirmationPage();
        dateUtil = new DateUtil();
        ATF_ID = PropertyManager.getInstance(true).getAtfId();
    }

    @Then("^ATF availability is set to (some availability|fully booked)$")
    public void atfAvailabilityIsSetToSomeAvailability(String availabilityType) throws ParseException {
        DynamoDbApi dynamoDbApi = new DynamoDbApi();
        Response response = dynamoDbApi.getAtfAvailabilityData(ATF_ID);
        Date expectedStartDate = Date.from(dateUtil.getNextMonday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expectedEndDate =
            Date.from(dateUtil.getNextMonday().plusDays(reportingPeriodLength).atStartOfDay(ZoneId.systemDefault()).toInstant());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String expectedStartDateFormatted = dateFormat.format(expectedStartDate);
        String expectedEndDateFormatted = dateFormat.format(expectedEndDate);

        response.then().body("availability.isAvailable", equalTo(availabilityType.equals("some availability")));
        response.then().body("availability.startDate", equalTo(expectedStartDateFormatted));
        response.then().body("availability.endDate", equalTo(expectedEndDateFormatted));
        response.then().body("name", equalTo(confirmationPage.getAtfName()));

        String lastUpdatedOn = response.jsonPath().get("availability.lastUpdated");
        Date lastUpdatedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(lastUpdatedOn);
        Date expectedLastUpdatedOn = dateUtil.getUtcNow();
        assertThat(lastUpdatedDate, DateMatchers.within(20, ChronoUnit.SECONDS, expectedLastUpdatedOn));
    }


    @And("ATF availability is not updated")
    public void atfAvailabilityIsNotUpdated() throws ParseException {
        DynamoDbApi dynamoDbApi = new DynamoDbApi();
        Response response = dynamoDbApi.getAtfAvailabilityData(ATF_ID);
        String lastUpdatedOn = response.jsonPath().get("availability.lastUpdated");
        Date lastUpdatedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(lastUpdatedOn);
        Date expectedLastUpdatedOn = dateUtil.getUtcNow();
        assertThat(lastUpdatedDate, not(DateMatchers.within(10, ChronoUnit.SECONDS, expectedLastUpdatedOn)));
    }
}

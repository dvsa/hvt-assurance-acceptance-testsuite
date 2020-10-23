package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.DynamoDbApi;
import io.restassured.response.Response;

public class NearestAtfResultsPage extends BasePage {

    private static final String headerText = "Test centres near ";
    protected RemoteWebDriver driver;
    private String headerId = "govuk-heading-xl";
    private String resultListElementXpath = "//ul[@class='govuk-list']";
    private String backButtonLinkText = "Back";
    private DynamoDbApi dynamoDbApi;

    public NearestAtfResultsPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        dynamoDbApi = new DynamoDbApi();
    }

    public NearestAtfResultsPage checkHeader(String postcode) {
        String actualHeaderText = getElementText(By.className(headerId));
        assertThat(actualHeaderText, is(equalTo(headerText + postcode)));
        return this;
    }

    public void checkResultsList() {
        List<WebElement> resultsElements = getElements(By.xpath(resultListElementXpath));
        assertThat(resultsElements, is(not(empty())));

        List<WebElement> actualAtfsWithNoInfo = getElementsWithText(resultsElements, "NO INFORMATION");
        List<WebElement> actualAvailableAtfs = getElementsWithText(resultsElements, "TESTS AVAILABLE");
        List<WebElement> actualFullyBookedAtfs = getElementsWithText(resultsElements, "FULLY BOOKED");

        Response response = dynamoDbApi.getNearestAtfs();
        JSONArray expectedAtfs = new JSONObject(response.asString()).getJSONArray("Items");
        JSONArray atfsWithNoInfo = new JSONArray();
        JSONArray availableAtfs = new JSONArray();
        JSONArray fullyBookedAtfs = new JSONArray();
        for (int i = 0; i < expectedAtfs.length(); i++) {
            JSONObject atf = expectedAtfs.getJSONObject(i);
            if (atf.has("availability") && atf.getJSONObject("availability").getBoolean("isAvailable")) {
                availableAtfs.put(atf);
            } else if (atf.has("availability") && !atf.getJSONObject("availability").getBoolean("isAvailable")) {
                fullyBookedAtfs.put(atf);
            } else {
                atfsWithNoInfo.put(atf);
            }
        }
        assertThat(actualAtfsWithNoInfo.size(), is(equalTo(atfsWithNoInfo.length())));
        assertThat(actualFullyBookedAtfs.size(), is(equalTo(fullyBookedAtfs.length())));
        assertThat(actualAvailableAtfs.size(), is(equalTo(availableAtfs.length())));

        checkAtfData(actualAvailableAtfs, availableAtfs);
        checkAtfData(actualFullyBookedAtfs, fullyBookedAtfs);
        checkAtfData(actualAtfsWithNoInfo, atfsWithNoInfo);
        //todo check LastUpdatedOn date and EndDate
    }

    private List<WebElement> getElementsWithText(List<WebElement> elements, String text) {
        return elements.stream()
            .filter(element -> element.getText().contains(text))
            .collect(Collectors.toList());
    }

    private void checkAtfData(List<WebElement> actualAtfs, JSONArray expectedAtfs) {
        for (int i = 0; i < expectedAtfs.length(); i++) {
            JSONObject expectedAtf = expectedAtfs.getJSONObject(i);
            String expectedAtfName = expectedAtf.getString("name");
            String expectedAtfAddress = expectedAtf.getJSONObject("address").getString("line1");
            String expectedAtfTown = expectedAtf.getJSONObject("address").getString("town");
            String expectedAtfPostcode = expectedAtf.getJSONObject("address").getString("postcode");
            String expectedAtfPhone = expectedAtf.getString("phone");
            String expectedAtfEmail = expectedAtf.getString("email");

            WebElement actualAtf = actualAtfs.stream()
                .filter(element -> element.getText().contains(expectedAtfName))
                .findAny()
                .get();

            String actualAtfName = actualAtf.findElement(By.className("govuk-link")).getText();
            String actualAtfAddress = actualAtf.findElement(By.className("dvsa-address")).getText();
            String actualAtfTown = actualAtf.findElement(By.className("dvsa-town")).getText();
            String actualAtfPostcode = actualAtf.findElement(By.className("dvsa-postcode")).getText();
            String actualAtfPhone = actualAtf.findElement(By.className("dvsa-phone")).getText();
            String actualAtfEmail = actualAtf.findElement(By.className("dvsa-email")).getText();

            assertThat(actualAtfName, is(equalTo(expectedAtfName)));
            assertThat(actualAtfAddress, is(equalTo(expectedAtfAddress)));
            assertThat(actualAtfTown, is(equalTo(expectedAtfTown)));
            assertThat(actualAtfPostcode, is(equalTo(expectedAtfPostcode)));
            assertThat(actualAtfPhone, is(equalTo(expectedAtfPhone)));
            assertThat(actualAtfEmail, is(equalTo(expectedAtfEmail)));
        }
    }

    public void clickBackButton() {
        clickElement(By.linkText(backButtonLinkText));
    }
}

package gov.hvtesting.pages;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import gov.hvtesting.framework.DynamoDbApi;
import io.restassured.response.Response;

public class NearestAtfResultsPage extends BasePage {

    private static final String headerText = "Test centres near ";
    private static final Integer maximumNumberOfResultsPerPage = 5;
    protected RemoteWebDriver driver;
    private String headerIdXpath = "//*[@id=\"main-content\"]/h1";
    private String resultListElementXpath = "//ul[@class='govuk-list']";
    private String testedAtThisCentreXpath = ".//p[text()='Tested at this centre']/following-sibling::*[1][name()='ul']";
    private String notTestedAtThisCentreXpath = ".//p[text()='Not tested at this centre']/following-sibling::*[1][name()='ul']";
    private String siteRestrictionsXpath = ".//p[text()='Site restrictions']/following-sibling::*[1][name()='ul']";
    private String categoryLinkXpath = ".//a[contains(text(),'category')]";
    private String availabilityStatusXpath = ".//strong";
    private String backButtonId = "back-link";
    private String pageLinkClass = "pagination__link";
    private String nextPageButtonXpath = "//a[@aria-label='Next page']";
    private String mileageFieldCss = "span.govuk-hint";
    private DynamoDbApi dynamoDbApi;
    private JSONArray expectedAtfsWithNoInfo;
    private JSONArray expectedAvailableAtfs;
    private JSONArray expectedFullyBookedAtfs;

    public NearestAtfResultsPage(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        dynamoDbApi = new DynamoDbApi();
    }

    public NearestAtfResultsPage checkHeader(String postcode) {
        String actualHeaderText = getElementText(By.xpath(headerIdXpath));
        assertThat(actualHeaderText, containsString(headerText + "'" + postcode + "'"));
        return this;
    }

    public void clickBackButton() {
        clickElement(By.id(backButtonId));
    }

    public void clickNextButton() {
        clickElement(By.xpath(nextPageButtonXpath));
    }

    public void checkResultsList() {
        List<WebElement> pages = getElements(By.className(pageLinkClass));
        pages.remove(pages.size() - 1);
        getExpectedAtfs();

        for (int i = 1; i < pages.size(); i++) {
            List<WebElement> resultsElements = getElements(By.xpath(resultListElementXpath));
            assertThat(resultsElements, is(not(empty())));
            assertThat(resultsElements.size(), is(lessThanOrEqualTo(maximumNumberOfResultsPerPage)));
            checkListOrdered(resultsElements);

            List<WebElement> actualAtfsWithNoInfo = getElementsWithText(resultsElements, "NO INFORMATION");
            List<WebElement> actualAvailableAtfs = getElementsWithText(resultsElements, "TESTS AVAILABLE");
            List<WebElement> actualFullyBookedAtfs = getElementsWithText(resultsElements, "FULLY BOOKED");

            checkAtfData(actualAtfsWithNoInfo, expectedAtfsWithNoInfo);
            checkAtfData(actualAvailableAtfs, expectedAvailableAtfs);
            checkAtfData(actualFullyBookedAtfs, expectedFullyBookedAtfs);

            clickElement(By.xpath(nextPageButtonXpath));
        }
        //todo check LastUpdatedOn date and EndDate
    }

    private void checkListOrdered(List<WebElement> resultsElements){
        List<Float> mileageElements = resultsElements.stream().map(el -> {
            WebElement element = el.findElement(By.cssSelector(mileageFieldCss));
            return Float.parseFloat(element.getText().replace(" miles away", ""));
        }).collect(Collectors.toList());
        List sorted = new ArrayList(mileageElements);
        Collections.sort(new ArrayList(mileageElements));
        assertThat(sorted.equals(mileageElements), is(true));
    }

    private List<WebElement> getElementsWithText(List<WebElement> elements, String text) {
        return elements.stream()
            .filter(element -> element.getText().contains(text))
            .collect(Collectors.toList());
    }

    private void checkAtfData(List<WebElement> actualAtfs, JSONArray expectedAtfs) {
        for (int i = 0; i < actualAtfs.size(); i++) {
            WebElement actualAtf = actualAtfs.get(i);
            String actualAtfName = actualAtf.findElement(By.className("govuk-link")).getText();
            String actualAtfAddress = actualAtf.findElement(By.className("dvsa-address")).getText().replace("\n", " ");
            String actualAtfTown = actualAtf.findElement(By.className("dvsa-town")).getText();
            String actualAtfPostcode = actualAtf.findElement(By.className("dvsa-postcode")).getText();
            String actualAtfPhone = actualAtf.findElement(By.className("dvsa-phone")).getText();
            String actualAtfEmail = actualAtf.findElement(By.className("dvsa-email")).getText();

            JSONObject expectedAtf = findJsonAtf(actualAtfName, expectedAtfs);
            String expectedAtfName = expectedAtf.getString("name");
            String expectedAtfAddress = expectedAtf.getJSONObject("address").getString("line1") + " " + expectedAtf.getJSONObject(
                "address").getString("line2");
            String expectedAtfTown = expectedAtf.getJSONObject("address").getString("town");
            String expectedAtfPostcode = expectedAtf.getJSONObject("address").getString("postcode");
            String expectedAtfPhone = expectedAtf.getString("phone");
            String expectedAtfEmail = expectedAtf.getString("email");
            JSONArray expectedTestedAtThisCentre = expectedAtf.getJSONArray("inclusions");
            JSONArray expectedNotTestedAtThisCentre = expectedAtf.getJSONArray("exclusions");
            JSONArray expectedSiteRestrictions = expectedAtf.getJSONArray("restrictions");

            assertThat(actualAtfName, is(equalTo(expectedAtfName)));
            assertThat(actualAtfAddress, is(equalTo(expectedAtfAddress.trim())));
            assertThat(actualAtfTown, is(equalTo(expectedAtfTown)));
            assertThat(actualAtfPostcode, is(equalTo(expectedAtfPostcode)));
            assertThat(actualAtfPhone, is(equalTo(expectedAtfPhone)));
            assertThat(actualAtfEmail, is(equalTo(expectedAtfEmail)));
            checkAdditionalInformation(actualAtf, expectedTestedAtThisCentre, testedAtThisCentreXpath, expectedAtfName);
            checkAdditionalInformation(actualAtf, expectedNotTestedAtThisCentre, notTestedAtThisCentreXpath,expectedAtfName);
            checkAdditionalInformation(actualAtf, expectedSiteRestrictions, siteRestrictionsXpath, expectedAtfName);
        }
    }

    private void checkAdditionalInformation(WebElement actualAtf, JSONArray expectedData, String xpath, String atfName){
        List<String> expectedDataList = expectedData.toList().stream().map(x->(String.valueOf(x).trim())).collect(Collectors.toList());
        if(expectedDataList.size()>0){
            List<String> actualData = Arrays.stream(actualAtf.findElement(By.xpath(xpath)).getText().split("\n"))
                .map(String::valueOf).collect(Collectors.toList());
            assertThat("failing for atf: " + atfName , actualData, Matchers.equalTo(expectedDataList));
            if(expectedDataList.toString().contains("category")){
                WebElement categoryLink = actualAtf.findElement(By.xpath(categoryLinkXpath));
                assertThat(categoryLink.isDisplayed(), is(true));
            }
        } else {
            assertThat(actualAtf.findElements(By.xpath(xpath)), is(empty()));
        }
    }

    private JSONObject findJsonAtf(String actualAtf, JSONArray expectedAtfs) {
        int i = 0;
        JSONObject json;
        do {
            json = expectedAtfs.getJSONObject(i);
            i++;
        } while (!json.getString("name").equals(actualAtf));
        return json;
    }

    private void getExpectedAtfs(){
        Response response = dynamoDbApi.getNearestAtfs();
        JSONArray expectedAtfs = new JSONObject(response.asString()).getJSONArray("Items");
        expectedAtfsWithNoInfo = new JSONArray();
        expectedAvailableAtfs = new JSONArray();
        expectedFullyBookedAtfs = new JSONArray();

        for (int i = 0; i < expectedAtfs.length(); i++) {
            JSONObject atf = expectedAtfs.getJSONObject(i);
            if (atf.has("availability") && atf.getJSONObject("availability").getBoolean("isAvailable")) {
                expectedAvailableAtfs.put(atf);
            } else if (atf.has("availability") && !atf.getJSONObject("availability").getBoolean("isAvailable")) {
                expectedFullyBookedAtfs.put(atf);
            } else {
                expectedAtfsWithNoInfo.put(atf);
            }
        }
    }

    public void checkAtfStatus(String atfName, String availability) {
        List<WebElement> resultsElements = getElements(By.xpath(resultListElementXpath));
        WebElement atfElement = getElementsWithText(resultsElements, atfName).get(0);
        String aftAvailabilityStatus = atfElement.findElement(By.xpath(availabilityStatusXpath)).getText();
        assertThat(aftAvailabilityStatus, is(equalTo(availability)));
    }
}

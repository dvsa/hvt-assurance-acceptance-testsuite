package gov.hvtesting.framework.TestData;

import gov.hvtesting.framework.dbModels.AddressItem;
import gov.hvtesting.framework.dbModels.AuthorisedTestingFacilitiesItem;
import gov.hvtesting.framework.dbModels.AvailabilityItem;
import gov.hvtesting.framework.dbModels.GeoLocationItem;

/**
 * Data for a valid ATF with No Information Availability and a valid Token to run Availability tests on
 **/
public class AtfTestDataWithToken {

    public String atfIdThree = "319bc3a0-7625-4b06-b1ba-c809c8e932b7";
    public AuthorisedTestingFacilitiesItem authorisedTestingFacilitiesItem;

    public AtfTestDataWithToken() {
        this.authorisedTestingFacilitiesItem = this.getAtfTestDataThree();
    }

    public String getAtfId(){
        return authorisedTestingFacilitiesItem.getId();
    }

    public String getAtfToken(){
        return authorisedTestingFacilitiesItem.getToken();
    }

    private AuthorisedTestingFacilitiesItem getAtfTestDataThree(){
        AuthorisedTestingFacilitiesItem atfItem = new AuthorisedTestingFacilitiesItem();
        atfItem.setId(atfIdThree);
        atfItem.setName("Acceptance Tests ATF With Token and Unknown Availability");
        atfItem.setAddress(getAddressItem());
        atfItem.setPhone("12345678");
        atfItem.setEmail("email@address.com");
        atfItem.setUrl("website.com");
        atfItem.setGeoLocation(getGeolocationItem());
        atfItem.setAvailability(getAvailabilityItem());

        return atfItem;
    }

    private AddressItem getAddressItem(){
        AddressItem addressItem = new AddressItem();
        addressItem.setLine1("The Garage");
        addressItem.setLine2("70 Derby Road");
        addressItem.setTown("Nottingham");
        addressItem.setPostcode("NG1 5FD");

        return addressItem;
    }

    private GeoLocationItem getGeolocationItem(){
        GeoLocationItem geoLocationItem = new GeoLocationItem();
        geoLocationItem.setLatitude(52.955392);
        geoLocationItem.setLongitude(-1.15832);

        return geoLocationItem;
    }

    private AvailabilityItem getAvailabilityItem(){
        AvailabilityItem availabilityItem = new AvailabilityItem();
        availabilityItem.setAvailable(true);
        availabilityItem.setLastUpdated("2021-02-22T00:00:00.000Z");
        availabilityItem.setEndDate("2021-03-22T00:00:00.000Z");
        availabilityItem.setStartDate("2021-02-22T00:00:00.000Z");

        return availabilityItem;
    }
}

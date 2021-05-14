package gov.hvtesting.framework.TestData;

import gov.hvtesting.framework.dbModels.AddressItem;
import gov.hvtesting.framework.dbModels.AuthorisedTestingFacilitiesItem;
import gov.hvtesting.framework.dbModels.AvailabilityItem;
import gov.hvtesting.framework.dbModels.GeoLocationItem;

/**
 * Data for a valid ATF with No Availability
**/
public class AtfTestDataNoAvailability {

    public String atfId = "3e5c5e29-1237-40db-b8ab-d2d13c967146";
    public AuthorisedTestingFacilitiesItem authorisedTestingFacilitiesItem;

    public AtfTestDataNoAvailability() {
        this.authorisedTestingFacilitiesItem = this.getAtfTestData();
    }

    private AuthorisedTestingFacilitiesItem getAtfTestData(){
        AuthorisedTestingFacilitiesItem atfItem = new AuthorisedTestingFacilitiesItem();
        atfItem.setId(atfId);
        atfItem.setName("Acceptance Tests ATF With No Availability");
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
        addressItem.setLine1("The Poynt Garage");
        addressItem.setLine2("Wollaton St");
        addressItem.setTown("Nottingham");
        addressItem.setPostcode("NG1 5FW");

        return addressItem;
    }

    private GeoLocationItem getGeolocationItem(){
        GeoLocationItem geoLocationItem = new GeoLocationItem();
        geoLocationItem.setLatitude(52.955491);
        geoLocationItem.setLongitude(-1.155413);

        return geoLocationItem;
    }

    private AvailabilityItem getAvailabilityItem(){
        return new AvailabilityItem().setAvailabilityDates(false);
    }
}

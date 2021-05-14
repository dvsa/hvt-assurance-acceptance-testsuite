package gov.hvtesting.framework.TestData;

import gov.hvtesting.framework.dbModels.AddressItem;
import gov.hvtesting.framework.dbModels.AuthorisedTestingFacilitiesItem;
import gov.hvtesting.framework.dbModels.AvailabilityItem;
import gov.hvtesting.framework.dbModels.GeoLocationItem;

/**
 * Data for a valid ATF with Availability
**/
public class AtfTestDataWithAvailability {

    public String atfId = "405810db-ed0e-4a45-9d41-2eadd3de6746";
    public AuthorisedTestingFacilitiesItem authorisedTestingFacilitiesItem;

    public AtfTestDataWithAvailability() {
        this.authorisedTestingFacilitiesItem = this.getAtfTestData();
    }

    private AuthorisedTestingFacilitiesItem getAtfTestData(){
        AuthorisedTestingFacilitiesItem atfItem = new AuthorisedTestingFacilitiesItem();
        atfItem.setId(atfId);
        atfItem.setName("Acceptance Tests ATF With Availability");
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
        addressItem.setLine1("Garage Axis Building");
        addressItem.setLine2("112 Upper Parliament St");
        addressItem.setTown("Nottingham");
        addressItem.setPostcode("NG1 6LP");

        return addressItem;
    }

    private GeoLocationItem getGeolocationItem(){
        GeoLocationItem geoLocationItem = new GeoLocationItem();
        geoLocationItem.setLatitude(52.954771);
        geoLocationItem.setLongitude(-1.154102);

        return geoLocationItem;
    }

    private AvailabilityItem getAvailabilityItem(){
        return new AvailabilityItem().setAvailabilityDates(true);
    }
}

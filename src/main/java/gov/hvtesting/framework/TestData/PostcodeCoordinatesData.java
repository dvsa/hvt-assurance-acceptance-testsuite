package gov.hvtesting.framework.TestData;

import gov.hvtesting.framework.dbModels.PostcodesCoordinatesItem;

public class PostcodeCoordinatesData {
    /**
     * Postcodes for the three new ATFS so we can search for them in the UI
     */

    public static PostcodesCoordinatesItem[] getPostcodes(){
        PostcodesCoordinatesItem postcodesCoordinatesItemOne = new PostcodesCoordinatesItem();
        postcodesCoordinatesItemOne.setPostcode("NG16LP");
        postcodesCoordinatesItemOne.setLatitude("52.954771");
        postcodesCoordinatesItemOne.setLongitude("-1.154102");

        PostcodesCoordinatesItem postcodesCoordinatesItemTwo = new PostcodesCoordinatesItem();
        postcodesCoordinatesItemTwo.setPostcode("NG15FW");
        postcodesCoordinatesItemTwo.setLatitude("52.955491");
        postcodesCoordinatesItemTwo.setLongitude("-1.155413");

        PostcodesCoordinatesItem postcodesCoordinatesItemThree = new PostcodesCoordinatesItem();
        postcodesCoordinatesItemThree.setPostcode("NG15FD");
        postcodesCoordinatesItemThree.setLatitude("52.955392");
        postcodesCoordinatesItemThree.setLongitude("-1.15832");

        return new PostcodesCoordinatesItem[]{postcodesCoordinatesItemOne, postcodesCoordinatesItemTwo, postcodesCoordinatesItemThree};
    }
}

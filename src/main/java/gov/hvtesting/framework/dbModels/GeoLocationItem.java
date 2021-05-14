package gov.hvtesting.framework.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

public class GeoLocationItem {

    public double latitude;
    public double longitude;

    @DynamoDBAttribute(attributeName="lat")
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName="long")
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}


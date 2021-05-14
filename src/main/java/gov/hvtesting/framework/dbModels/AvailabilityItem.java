package gov.hvtesting.framework.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import gov.hvtesting.framework.dbModels.Decorator.AvailabilityItemDecorator;

@DynamoDBDocument
public class AvailabilityItem {

    /**
     * Use a decorater to extend functionality so we can test the logic separately from the model
     * TODO - check implementation
     */
    private static final AvailabilityItemDecorator DECORATOR = new AvailabilityItemDecorator();
    private boolean isAvailable;
    private String lastUpdated;
    private String endDate;
    private String startDate;

    public AvailabilityItem() { }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName="isAvailable")
    public boolean getIsAvailable() { return isAvailable; }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBAttribute(attributeName="lastUpdated")
    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBAttribute(attributeName="endDate")
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBAttribute(attributeName="startDate")
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @DynamoDBIgnore
    public AvailabilityItem setAvailabilityDates() {
        return DECORATOR.decorate(this);
    }

    @DynamoDBIgnore
    public AvailabilityItem setAvailabilityDates(Boolean isAvailable){
        return DECORATOR.decorate(this, isAvailable);
    }

}


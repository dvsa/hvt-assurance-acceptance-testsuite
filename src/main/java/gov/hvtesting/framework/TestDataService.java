package gov.hvtesting.framework;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import gov.hvtesting.framework.TestData.AtfTestDataWithAvailability;
import gov.hvtesting.framework.TestData.AtfTestDataWithToken;
import gov.hvtesting.framework.TestData.AtfTestDataNoAvailability;
import gov.hvtesting.framework.TestData.PostcodeCoordinatesData;

import java.util.Arrays;

public class TestDataService {

    AmazonDynamoDB client;
    DynamoDBMapper mapper;
    AtfTestDataWithAvailability atfTestDataWithAvailability;
    AtfTestDataNoAvailability atfTestDataNoAvailability;
    AtfTestDataWithToken atfTestDataWithToken;

    public TestDataService(){
        PropertyManager propertyManager = PropertyManager.getInstance();

        // TODO this and the other db writes should be moved into the DynamoDBApi
        client = propertyManager.isLocal() ?
                AmazonDynamoDBClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(propertyManager.getDynamoDbUrl(), "eu-west-1"))
                        .build()
                :
                AmazonDynamoDBClientBuilder.standard()
                        .build();

        mapper = new DynamoDBMapper(this.client);
        atfTestDataWithAvailability = new AtfTestDataWithAvailability();
        atfTestDataNoAvailability = new AtfTestDataNoAvailability();
        atfTestDataWithToken = new AtfTestDataWithToken();
    }

    public void setupTestData() throws Exception {
        try{
            this.createAtfs();
            this.setTokens();
        }
        catch (Exception ex){
            throw new Exception("Error setting up the test data in the table: " + ex.getMessage());
        }
    }

    public void tearDownTestData() throws Exception {
        try{
            this.deleteAtfs();
        }
        catch (Exception ex){
            throw new Exception("Error deleting the test data: " + ex.getMessage());
        }
    }

    /**
        ATF Availability data is pulled in and written to the AuthorisedTestingFacilities table
        Instead of hard coded classes, a CSV file could be pulled in with more data for testing
        Dates for the availability of the atf are worked out dynamically so the ATFS have current availability statuses
    **/
    private void createAtfs(){
        this.mapper.batchSave(
                atfTestDataWithAvailability.authorisedTestingFacilitiesItem,
                atfTestDataNoAvailability.authorisedTestingFacilitiesItem,
                atfTestDataWithToken.authorisedTestingFacilitiesItem
        );
        this.mapper.batchSave(Arrays.asList(PostcodeCoordinatesData.getPostcodes()));
    }

    private void deleteAtfs(){
        this.mapper.batchDelete(
                atfTestDataWithAvailability.authorisedTestingFacilitiesItem,
                atfTestDataNoAvailability.authorisedTestingFacilitiesItem,
                atfTestDataWithToken.authorisedTestingFacilitiesItem
        );
        this.mapper.batchDelete(Arrays.asList(PostcodeCoordinatesData.getPostcodes()));
    }

    private void setTokens(){
        TokenGenerator tokenGenerator = new TokenGenerator();
        tokenGenerator.generateToken(atfTestDataWithToken.getAtfToken());
    }

}

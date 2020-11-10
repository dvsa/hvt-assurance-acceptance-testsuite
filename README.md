# HV Testing - Acceptance Testing Framework

This is an acceptance testing framework for the Heavy Vehicle Testing. A service which was created during COVID-19 to help heavy vehicles owners to find testing site which had availability for testing the vehicles.   

This project uses GOV.UK Notify API, to read more about this please click [here](https://www.notifications.service.gov.uk/documentation)

##Getting Started 
To get this testing framework working you will need to install a Chromedriver. This can be done by using the site below to download the correct Chromedriver, which will work with your version of Chrome on your machine. 

[ChromeDriver - WebDriver for Chrome](https://chromedriver.chromium.org/downloads)

You will need to add the ChromeDrive here `src/test/resources/driver` 

More information about ChromeDriver can be found in the driver.md file under the following section `src/test/resources/driver`


You will need to include a test.properties file in the framework, see the example file (example-test.properties) to help you create your own file, this will need to be named test.properties.


##Running tests

In order to run all tests locally, run the following command:

```shell
mvn test
```
## In order to run all tests locally, run the following command:
mvn test -Denvironment="local"

To run selected test(s), described with @myTag:. Tag avaliable are shown in the Tags section
```shell
mvn test -Dcucumber.filter.tags="@myTag"
## To run selected test(s), described with @myTag:.
mvn test -Denvironment="local" -Dcucumber.filter.tags="@myTag"

## To run on selected environment: local or remote
mvn test -Denvironment="local" -Dcucumber.filter.tags="@myTag"
mvn test -Denvironment="remote" -Dcucumber.filter.tags="@myTag"

```

####Tags
```shell
@UI
@Cookies
@TestData
```


More information of how the configuration of how to run tests can be found in `TestRunner.java` class. 

##Reporting
After every run new cucumber Test Report is generated and can be found in `target/cucumber-reports.html`

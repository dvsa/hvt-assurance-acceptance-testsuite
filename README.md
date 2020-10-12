# acceptance-tests

HV Testing - acceptance testing framework

#Running tests
Tests running configuration can be found in `TestRunner.java` class. 

```shell
## In order to run all tests locally, run the following command:
mvn test

## To run selected test(s), described with @myTag:.
mvn test -Dcucumber.filter.tags="@myTag"
```
#GOV.UK Notify setup
In order to use GOV.UK Notify API properly, initial setup is required:
1. Go to https://www.notifications.service.gov.uk/documentation
2. Select JAVA in `Client libraries` section.
3. Follow the instruction described in `Install the client` section.This will include downloading `settings.xml` file (http://maven.apache.org/settings.html) and adding pom dependency

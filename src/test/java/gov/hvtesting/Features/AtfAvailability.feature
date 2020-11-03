Feature: ATF's availability

  @UI @TC3 @TestData
  Scenario: ATF can update the availability as fully booked
    Given I choose to fully booked link
    When I am on Fully Booked confirmation page
    Then ATF availability is set to fully booked

  @UI @TC4 @TestData
  Scenario: ATF can update the availability as some availability
    And I choose to some availability link
    Then I am on Some Availability confirmation page
    And ATF availability is set to some availability

  @UI @TC5 @TestData
  Scenario: ATF can change the availability to some availability from the same link
    Given I choose to fully booked link
    When I am on Fully Booked confirmation page
    Then ATF availability is set to fully booked
    When I choose to some availability link
    Then I am on Some Availability confirmation page
    And ATF availability is set to some availability

  @UI @TC8 @TestData
  Scenario: ATF updates availability with invalid token
    Given I choose to fully booked link with cut out token
    Then I am on Service Unavailable page

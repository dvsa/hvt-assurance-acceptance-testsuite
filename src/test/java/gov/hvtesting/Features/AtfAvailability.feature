Feature: ATF's availability

  @UI @TestData
  Scenario: ATF can update the availability as fully booked
    Given I click the link to update the availability to fully booked
    Then I check if I am on choose my availability page
    And I select No radio button
    And I submit my availability
    When I am on Fully Booked confirmation page


  @UI @TestData
  Scenario: ATF can update the availability as some availability
    Given I click the link to update the availability to yes we have availability
    Then I check if I am on choose my availability page
    And I select Yes radio button
    And I submit my availability
    Then I am on Some Availability confirmation page

  @UI @TestData
  Scenario: ATF can change the availability to some availability from the same link
    Given I click the link to update the availability to fully booked
    Then I check if I am on choose my availability page
    And I select No radio button
    And I submit my availability
    Then I am on Fully Booked confirmation page
    And I click the link to update the availability to yes we have availability
    Then I check if I am on choose my availability page
    And I select Yes radio button
    And I submit my availability
    Then I am on Some Availability confirmation page

  @UI @TestData
  Scenario: ATF updates availability with invalid token
    Given I click the link to update the availability without out token
    Then I am on Service Unavailable page

Feature: ATF's availability

  @UI @TC3
  Scenario: ATF can update the availability as fully booked
    Given I choose to fully booked link
    When I am on Fully Booked confirmation page
    Then ATF availability is set to fully booked

  @UI @TC4
  Scenario: ATF can update the availability as some availability
    And I choose to some availability link
    Then I am on Some Availability confirmation page
    And ATF availability is set to some availability

  @UI @TC5
  Scenario: ATF can change the availability to some availability from the same link
    Given I choose to fully booked link
    When I am on Fully Booked confirmation page
    Then ATF availability is set to fully booked
    When I choose to some availability link
    Then I am on Some Availability confirmation page
    And ATF availability is set to some availability

  @UI @TC6
  Scenario: ATF cannot see if there was an error on updating availability
    And I choose to some availability link
    Then I am on Some Availability confirmation page
    And ATF availability is not updated
    #is it possible to automate?

  @UI  @TC7 @todo
  Scenario: Some availability link expires after the given time
    When I choose to some availability link with expired link
    Then I get link expired message

  @UI   @TC8 @todo
  Scenario: Fully booked link expires after the given time
    When I choose to fully booked link with expired link
    Then I get link expired message

  @UI @TC9 @todo
  Scenario: ATF can ask for resending email
    When I choose to fully booked link
    And I get link expired message
    And I ask for resending an email
    Then I get another email

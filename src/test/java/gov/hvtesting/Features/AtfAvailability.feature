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

  @UI @TC7
  Scenario: Some availability link expires after the given time
    When I choose to some availability link with expired link
    Then I am on link expired page
    And ATF availability is not updated

  @UI @TC9
  Scenario: ATF can ask for resending email
    When I choose to fully booked link with expired link
    Then I am on link expired page
    And ATF availability is not updated
    When I ask for resending an email
    Then I am on Email resent page
    #todo check if email got here with Notifier

  @UI @TC8
  Scenario Outline: ATF updates availability with invalid token
    Given I choose to fully booked link with invalid token for a "<reason>"
    Then I am on Service Unavailable page
    Examples:
      | reason                |
      | incorrect_secret      |
      | token_end_cut_out     |
      | token_missing_payload |
      | non_existing_atf      |

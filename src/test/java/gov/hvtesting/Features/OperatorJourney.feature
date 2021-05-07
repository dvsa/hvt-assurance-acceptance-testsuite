@OperatorJourney
Feature: Operator Journey

  @UI
  Scenario: Operator can search for nearest ATF in UI and go back to search page
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "AB101AA"
    Then I am on Nearest ATF Results page for "AB10 1AA"
    When I click back button
    Then I am on Nearest ATF Search page


  @UI
  Scenario: Operator can search for nearest ATF in UI
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "AB101AA"
    Then I am on Nearest ATF Results page for "AB10 1AA"
    And I can see 50 nearest ATFs ordered

  @UI
  Scenario: user cannot search for nearest ATF in UI with invalid inputs
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "pll777"
    Then I can see error message

  @UI
  Scenario: User decides to remove test centres with no availability from the results
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "SW1A2AA"
    Then I am on Nearest ATF Results page for "SW1A 2AA"
    Then I click on the option to only see centres with tests available
    And The page does not contain "FULLY BOOKED"

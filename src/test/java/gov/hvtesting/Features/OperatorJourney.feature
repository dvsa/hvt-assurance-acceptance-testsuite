Feature: Operator Journey

  @UI @TC12
  Scenario: Operator can search for nearest ATF in UI and go back to search page
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "AB101AA"
    Then I am on Nearest ATF Results page for "AB10 1AA"
    When I click back button
    Then I am on Nearest ATF Search page


  @UI @TC13
  Scenario: Operator can search for nearest ATF in UI
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "AB101AA"
    Then I am on Nearest ATF Results page for "AB10 1AA"
    And I can see 50 nearest ATFs ordered

  @UI @TC16
  Scenario: user cannot search for nearest ATF in UI with invalid inputs
    Given I go to Nearest ATF Search page
    When I fill in my postcode with "pll777"
    Then I can see error message

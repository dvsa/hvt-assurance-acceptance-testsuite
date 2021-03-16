@Cookies
Feature: Cookies Tests

  @UI
  Scenario: User navigates to the Find a test centre for an HGV, bus or trailer MOT and the cookie banner is displayed
    Given I go to Nearest ATF Search page
    Then I should be present with the cookie banner
    And The banner should contain the following cookie banner wording

  @UI
  Scenario: Scenario: User accepts cookies for the Find a test centre for an HGV, bus or trailer MOT and check that the correct cookies are set
    Given I go to Nearest ATF Search page
    Then I should be present with the cookie banner
    And I click the Accept all cookies button
    Then I am presented with the success banner and I click Hide
    When I fill in my postcode with "AB101AA"
    Then I am on Nearest ATF Results page for "AB10 1AA"
    When I click back button
    Then I check the "cm-user-preferences" cookie is set
    Then I check the "_ga" cookie is set

  @UI
  Scenario: User switches off GA cookie preferences
    Given I go to Nearest ATF Search page
    Then I should be present with the cookie banner
    And I click the Set cookie preferences cookies button
    Then I should be on Cookies for the "Cookies for the Find a test centre for an HGV, bus or trailer MOT" page
    Then I select Off Google Analytics cookies
    And I click Save changes button
    Then I check the "cm-user-preferences" cookie is set
    Then I check the "_ga" cookie is not set

Feature: ATF's data prepare

  @UATData
  Scenario: Prepare availability data for ATFs
    Given I choose "fully booked" link for "ATF2"
    And I choose "some availability" link for "ATF3"
    And I choose "fully booked" link for "ATF6a"
    And I choose "some availability" link for "ATF6b"
    And I choose "some availability" link for "ATF7a"
    And I choose "fully booked" link for "ATF7b"
    And I choose "fully booked" link for "ATF8"
    When I go to Nearest ATF Search page
    When I fill in my postcode with "LN11JF"
    Then I am on Nearest ATF Results page for "LN1 1JF"
    And I can see 'ATF2' to have FULLY BOOKED
    And I can see 'ATF3' to have TESTS AVAILABLE
    And I can see 'ATF4' to have NO INFORMATION
    And I can see 'ATF5' to have NO INFORMATION
    And I can see 'ATF6a' to have FULLY BOOKED
    When I click next button
    And I can see 'ATF6b' to have TESTS AVAILABLE
    And I can see 'ATF7a' to have TESTS AVAILABLE
    And I can see 'ATF7b' to have FULLY BOOKED
    And I can see 'ATF8' to have FULLY BOOKED
    And I can see 'ATF9' to have NO INFORMATION




Feature: Reminder emails sent to ATFs

  @TC1 @todo
  Scenario: Reminder emails are sent to ATF by Notifier
    Given I sent reminder emails
    When I check notifier
    Then All emails are sent successfully

  @TC2 @todo
  Scenario: Reminder emails have correct content
    Given I sent reminder emails
    When I catch an email
    Then an email has correct content

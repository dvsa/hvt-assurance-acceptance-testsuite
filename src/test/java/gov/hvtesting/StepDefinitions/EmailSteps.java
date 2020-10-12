package gov.hvtesting.StepDefinitions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;

import gov.hvtesting.framework.EmailsApi;
import gov.hvtesting.framework.TestContext;
import gov.hvtesting.pages.ConfirmationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.service.notify.Notification;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;

public class EmailSteps {

    @Given("I sent reminder emails")
    public void iSentReminderEmails() throws IOException, InterruptedException {
        EmailsApi emailsApi = new EmailsApi();
        emailsApi.triggerSendingEmailsJob();
    }

    @When("I check notifier")
    public void iCheckNotifier() throws NotificationClientException {
        //todo get apiKey
        String apiKey = "";
        NotificationClient client = new NotificationClient(apiKey);
        String notificationId = "";
        //todo get notificationId
        Notification notification = client.getNotificationById(notificationId);
        assertThat(notification.getStatus(), equalTo("Delivered"));
    }

    @Then("All emails are sent successfully")
    public void allEmailsAreSentSuccessfully() {
    }

    @When("I catch an email")
    public void iCatchAnEmail() {
    }

    @Then("an email has correct content")
    public void anEmailHasCorrectContent() {
        //two links are there
    }
}

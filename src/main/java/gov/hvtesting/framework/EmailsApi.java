package gov.hvtesting.framework;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class EmailsApi {

    //jenkins_url/job/job_name/buildWithParameters?token=TOKEN&parameter=VALUE

    public EmailsApi() {
    }

    public void triggerSendingEmailsJob() throws IOException, InterruptedException {
        HashMap<String, String> values = new HashMap<String, String>() {{
            put("token", "value1");
            put("param2", "value2");
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
            .writeValueAsString(values);

        String jenkinsUrl = PropertyManager.getInstance(true).getJenkinsUrl();
        String jobName = PropertyManager.getInstance(true).getSendReminderEmailJobName();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(jenkinsUrl + "/job/" + jobName + "/buildWithParameters"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));

        // it will trigger jenkins job that will trigger token-generator lambda which will:
//        1. fetch email template from S3
//        2. download all atfs data from db
//        3. generates 2 tokens for each atf
//        4. puts emails to queue

    }
}

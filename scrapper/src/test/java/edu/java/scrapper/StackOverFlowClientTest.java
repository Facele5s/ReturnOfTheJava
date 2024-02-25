package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.stackoverflow.StackOverFlowClient;
import edu.java.client.stackoverflow.StackOverFlowResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StackOverFlowClientTest {
    private static WireMockServer wireMockServer;
    private static StackOverFlowClient stackOverFlowClient;
    private static String baseUrl = "http://localhost:8080";

    @BeforeAll
    public static void setup() {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        stackOverFlowClient = new StackOverFlowClient(client);

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public static void stop() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Getting question info")
    public void getQuestionInfo() throws IOException {
        File file = new File("src/test/resources/StackOverFlowResponseExample.json");
        String responseString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        stubFor(get("/questions/111?site=stackoverflow")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        StackOverFlowResponse response = stackOverFlowClient.getResponse(111);

        assertEquals("2023-04-08T13:54:38Z", response.items().getFirst().lastActivityDate().toString());
    }
}

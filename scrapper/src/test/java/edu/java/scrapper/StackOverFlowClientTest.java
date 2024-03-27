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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackOverFlowClientTest {
    private static final File FILE = new File("src/test/resources/StackOverFlowResponseExample.json");

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
    @DisplayName("Question info test")
    public void getQuestionInfoTest() throws IOException {
        //Arrange
        String responseString = FileUtils.readFileToString(FILE, StandardCharsets.UTF_8);
        stubFor(get("/questions/111?site=stackoverflow")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        //Act
        StackOverFlowResponse response = stackOverFlowClient.getResponse(111);

        //Assert
        assertEquals("2023-04-08T13:54:38Z", response.items().getFirst().lastActivityDate().toString());
    }
}

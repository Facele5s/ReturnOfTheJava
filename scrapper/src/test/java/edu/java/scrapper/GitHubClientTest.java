package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.github.GitHubClient;
import edu.java.client.github.GitHubResponse;
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

public class GitHubClientTest {
    private static WireMockServer wireMockServer;
    private static GitHubClient gitHubClient;
    private static String baseUrl = "http://localhost:8080";

    @BeforeAll
    public static void setup() {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        gitHubClient = new GitHubClient(client);

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public static void stop() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Getting repository info")
    public void getRepoInfo() throws IOException {
        File file = new File("src/test/resources/GitHubResponseExample.json");
        String responseString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        stubFor(get("/repos/testUser/testRepo")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        GitHubResponse response = gitHubClient.getResponse("testUser", "testRepo");

        assertEquals(674765843, response.id());
        assertEquals("Graph_GUI", response.name());
        assertEquals("2023-08-12T00:48:01Z", response.pushedAt().toString());
        assertEquals("2023-08-04T23:55:05Z", response.updatedAt().toString());
    }
}

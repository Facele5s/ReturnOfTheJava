package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.github.GitHubClient;
import edu.java.client.github.model.Commit;
import edu.java.client.github.model.Pull;
import edu.java.client.github.model.Release;
import edu.java.client.github.model.Repo;
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
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubClientTest {
    private static final File FILE_REPO = new File("src/test/resources/GitHubRepoExample.json");
    private static final File FILE_COMMITS = new File("src/test/resources/GitHubCommitsExample.json");
    private static final File FILE_PULLS = new File("src/test/resources/GitHubPullsExample.json");
    private static final File FILE_RELEASES = new File("src/test/resources/GitHubReleasesExample.json");

    private static WireMockServer wireMockServer;
    private static GitHubClient gitHubClient;
    private static String baseUrl = "http://localhost:8080";

    @BeforeAll
    public static void setup() {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        gitHubClient = new GitHubClient(client, null, null, null, null);

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public static void stop() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Repository info test")
    public void getRepoInfoTest() throws IOException {
        //Arrange
        String responseString = FileUtils.readFileToString(FILE_REPO, StandardCharsets.UTF_8);
        stubFor(get("/repos/testUser/testRepo")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        //Act
        Repo repository = gitHubClient.getRepo("testUser", "testRepo");

        //Assert
        assertEquals(674765843, repository.getId());
        assertEquals("Graph_GUI", repository.getName());
    }

    @Test
    @DisplayName("Commits info test")
    public void getCommitsInfoTest() throws IOException {
        //Arrange
        String responseString = FileUtils.readFileToString(FILE_COMMITS, StandardCharsets.UTF_8);
        stubFor(get("/repos/testUser/testRepo/commits")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        //Act
        List<Commit> commits = gitHubClient.getCommits("testUser", "testRepo");

        //Assert
        assertEquals(7, commits.size());
        assertEquals("2abc5ed3c55fb4bf6774ab900ca51116bed97f22", commits.getFirst().getSha());
        assertEquals("ff3b1f9df6939a7f4036f9a57c87fe11f5335995", commits.getLast().getSha());
    }

    @Test
    @DisplayName("Pulls info test")
    public void getPullsInfoTest() throws IOException {
        //Arrange
        String responseString = FileUtils.readFileToString(FILE_PULLS, StandardCharsets.UTF_8);
        stubFor(get("/repos/testUser/testRepo/pulls")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        //Act
        List<Pull> pulls = gitHubClient.getPulls("testUser", "testRepo");

        //Assert
        assertEquals(1, pulls.size());
        assertEquals(1810385783, pulls.getFirst().getId());
    }

    @Test
    @DisplayName("Releases info test")
    public void getReleasesInfoTest() throws IOException {
        //Arrange
        String responseString = FileUtils.readFileToString(FILE_RELEASES, StandardCharsets.UTF_8);
        stubFor(get("/repos/testUser/testRepo/releases")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseString))
        );

        //Act
        List<Release> releases = gitHubClient.getReleases("testUser", "testRepo");

        //Assert
        assertEquals(1, releases.size());
        assertEquals(150052129, releases.getFirst().getId());
    }
}

package edu.java.client.github;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(@Qualifier("githubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse getResponse(String userName, String repoName) {
        return webClient
            .get()
            .uri("/repos/{userName}/{repoName}", userName, repoName)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }
}

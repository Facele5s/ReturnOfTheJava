package edu.java.client.github;

import edu.java.client.Client;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@SuppressWarnings("MagicNumber")
public class GitHubClient implements Client {
    private static final String URL_PATTERN = "^((http)s?://)?github\\.com/(\\w+)/(\\w+)$";

    private final WebClient webClient;

    public GitHubClient(@Qualifier("githubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse getResponse(URI url) {
        Matcher matcher = Pattern.compile(URL_PATTERN).matcher(url.toString());
        matcher.matches();

        String userName = matcher.group(3);
        String repoName = matcher.group(4);

        return webClient
            .get()
            .uri("/repos/{userName}/{repoName}", userName, repoName)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }

    @Override
    public boolean isLinkSupported(URI url) {
        return url.toString().matches(URL_PATTERN);
    }
}

package edu.java.client.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.client.Client;
import edu.java.client.Response;
import edu.java.client.github.model.Commit;
import edu.java.client.github.model.Pull;
import edu.java.client.github.model.Release;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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
    public String getUpdateDescription(Response response) {
        OffsetDateTime updateDate = response.getUpdateDate();
        String userName = response.getParams().get(0);
        String repoName = response.getParams().get(1);
        String description = "";

        if (isCommited(updateDate, userName, repoName)) {
            description = "There is a new commit!";
        }

        if (description.isEmpty() && isPulled(updateDate, userName, repoName)) {
            description = "There is a new pull request!";
        }

        if (description.isEmpty() && isReleased(updateDate, userName, repoName)) {
            description = "There is a new release!";
        }

        return description;
    }

    @Override
    public boolean isLinkSupported(URI url) {
        return url.toString().matches(URL_PATTERN);
    }

    private boolean isCommited(OffsetDateTime dateTime, String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoName}/commits", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class).block();

        List<Commit> commits = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Commit.class))
            .toList();

        return !commits.isEmpty() && commits.getFirst().getDate().isEqual(dateTime);
    }

    private boolean isPulled(OffsetDateTime dateTime, String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoName}/pulls", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class).block();

        List<Pull> pulls = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Pull.class))
            .toList();

        return !pulls.isEmpty() && pulls.getFirst().getDate().isEqual(dateTime);
    }

    private boolean isReleased(OffsetDateTime dateTime, String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoName}/releases", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class).block();

        List<Release> releases = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Release.class))
            .toList();

        return !releases.isEmpty() && releases.getFirst().getDate().isEqual(dateTime);
    }

}

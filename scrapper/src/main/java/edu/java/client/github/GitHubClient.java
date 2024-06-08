package edu.java.client.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.client.Client;
import edu.java.client.github.model.Commit;
import edu.java.client.github.model.Pull;
import edu.java.client.github.model.Release;
import edu.java.client.github.model.Repo;
import edu.java.dto.exception.BadRequestException;
import edu.java.entity.GithubCommit;
import edu.java.entity.GithubPull;
import edu.java.entity.GithubRelease;
import edu.java.service.GithubCommitService;
import edu.java.service.GithubPullService;
import edu.java.service.GithubReleaseService;
import edu.java.service.GithubRepositoryService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
@RequiredArgsConstructor
@Slf4j
public class GitHubClient implements Client {
    private static final String URL_PATTERN = "^((http)s?://)?github\\.com/(\\w+)/(\\w+)$";
    private static final String UPD_COMMIT = "A new commit";
    private static final String UPD_PULL = "A new pull request";
    private static final String UPD_RELEASE = "A new release";

    @Qualifier("githubWebClient")
    private final WebClient webClient;

    private final GithubRepositoryService repoService;
    private final GithubCommitService commitService;
    private final GithubPullService pullService;
    private final GithubReleaseService releaseService;

    public GitHubResponse getResponse(URI url) {
        List<String> updateReasons = new ArrayList<>();

        Map<String, String> repoData = parseRepoData(url);
        String userName = repoData.get("USERNAME");
        String repoName = repoData.get("REPONAME");

        Repo repo = getRepo(userName, repoName);

        OffsetDateTime lastUpdate = null;

        GithubCommit lastCommit = commitService.getLast();
        List<Commit> newCommits = getCommits(userName, repoName).stream()
            .filter(lastCommit != null ? c -> c.getDate().isAfter(lastCommit.getCreatedAt()) : c -> true)
            .toList();

        if (!newCommits.isEmpty()) {
            updateReasons.add(UPD_COMMIT);
            lastUpdate = newCommits.getFirst().getDate();
            newCommits.forEach(c -> {
                try {
                    commitService.add(
                        c.getSha(), repo.getId(), c.getAuthor(), c.getDate()
                    );
                } catch (BadRequestException e) {
                    log.error(e.getDescription());
                }
            });
        }

        GithubPull lastPull = pullService.getLast();
        List<Pull> newPulls = getPulls(userName, repoName).stream()
            .filter(lastPull != null ? p -> p.getDate().isAfter(lastPull.getCreatedAt()) : p -> true)
            .toList();

        if (!newPulls.isEmpty()) {
            updateReasons.add(UPD_PULL);
            OffsetDateTime newPullDate = newPulls.getFirst().getDate();
            lastUpdate = newPullDate.isAfter(lastUpdate) ? newPullDate : lastUpdate;

            newPulls.forEach(p -> {
                try {
                    pullService.add(
                        p.getId(), repo.getId(), p.getDate()
                    );
                } catch (BadRequestException e) {
                    log.error(e.getDescription());
                }
            });
        }

        GithubRelease lastRelease = releaseService.getLast();
        List<Release> newReleases = getReleases(userName, repoName).stream()
            .filter(lastRelease != null ? r -> r.getDate().isAfter(lastRelease.getPublishedAt()) : r -> true)
            .toList();

        if (!newReleases.isEmpty()) {
            updateReasons.add(UPD_RELEASE);
            OffsetDateTime newReleaseDate = newReleases.getFirst().getDate();
            lastUpdate = newReleaseDate.isAfter(lastUpdate) ? newReleaseDate : lastUpdate;

            newReleases.forEach(r -> {
                try {
                    releaseService.add(
                        r.getId(), repo.getId(), r.getDate()
                    );
                } catch (BadRequestException e) {
                    log.error(e.getDescription());
                }
            });
        }

        return new GitHubResponse(updateReasons, lastUpdate);
    }

    @Override
    public boolean isLinkSupported(URI url) {
        return url.toString().matches(URL_PATTERN);
    }

    public Repo getRepo(String userName, String repoName) {
        return webClient
            .get()
            .uri("/repos/{userName}/{repoName}", userName, repoName)
            .retrieve()
            .bodyToMono(Repo.class)
            .block();
    }

    public List<Commit> getCommits(String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoNane}/commits", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class)
            .block();

        List<Commit> commits = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Commit.class))
            .toList();

        return commits;
    }

    public List<Pull> getPulls(String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoNane}/pulls", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class)
            .block();

        List<Pull> pulls = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Pull.class))
            .toList();

        return pulls;
    }

    public List<Release> getReleases(String userName, String repoName) {
        ObjectMapper mapper = new ObjectMapper();

        Object[] objects = webClient.get()
            .uri("/repos/{userName}/{repoNane}/releases", userName, repoName)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class)
            .block();

        List<Release> releases = Arrays.stream(objects)
            .map(o -> mapper.convertValue(o, Release.class))
            .toList();

        return releases;
    }

    @Override
    public void addLinkData(URI url, Long linkId) {
        Map<String, String> repoData = parseRepoData(url);
        String userName = repoData.get("USERNAME");
        String repoName = repoData.get("REPONAME");

        Repo repo = getRepo(userName, repoName);
        try {
            repoService.add(repo.getId(), userName, repoName);
        } catch (BadRequestException e) {
            log.error(e.getDescription());
        }

        List<Commit> commits = getCommits(userName, repoName);
        commits.forEach(c -> {
            try {
                commitService.add(
                    c.getSha(), repo.getId(), c.getAuthor(), c.getDate()
                );
            } catch (BadRequestException e) {
                log.error(e.getDescription());
            }
        });

        List<Pull> pulls = getPulls(userName, repoName);
        pulls.forEach(p -> {
            try {
                pullService.add(
                    p.getId(), repo.getId(), p.getDate()
                );
            } catch (BadRequestException e) {
                log.error(e.getDescription());
            }
        });

        List<Release> releases = getReleases(userName, repoName);
        releases.forEach(r -> {
            try {
                releaseService.add(
                    r.getId(), repo.getId(), r.getDate()
                );
            } catch (BadRequestException e) {
                log.error(e.getDescription());
            }
        });
    }

    private Map<String, String> parseRepoData(URI url) {
        Matcher matcher = Pattern.compile(URL_PATTERN).matcher(url.toString());
        Map<String, String> repoData = new HashMap<>();
        matcher.matches();

        String userName = matcher.group(3);
        String repoName = matcher.group(4);

        repoData.put("USERNAME", userName);
        repoData.put("REPONAME", repoName);

        return repoData;
    }
}

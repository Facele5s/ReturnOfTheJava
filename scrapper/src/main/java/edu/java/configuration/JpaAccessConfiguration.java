package edu.java.configuration;

import edu.java.client.Client;
import edu.java.repository.JpaChatRepository;
import edu.java.repository.JpaGithubCommitRepository;
import edu.java.repository.JpaGithubPullRepository;
import edu.java.repository.JpaGithubReleaseRepository;
import edu.java.repository.JpaGithubRepoRepository;
import edu.java.repository.JpaLinkRepository;
import edu.java.service.ChatService;
import edu.java.service.GithubCommitService;
import edu.java.service.GithubPullService;
import edu.java.service.GithubReleaseService;
import edu.java.service.GithubRepositoryService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaGithubCommitService;
import edu.java.service.jpa.JpaGithubPullService;
import edu.java.service.jpa.JpaGithubReleaseService;
import edu.java.service.jpa.JpaGithubRepositoryService;
import edu.java.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public ChatService chatService(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        return new JpaChatService(jpaChatRepository, jpaLinkRepository);
    }

    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository,
        List<Client> availableClients
        ) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository, availableClients);
    }

    @Bean
    public GithubRepositoryService githubRepositoryService(JpaGithubRepoRepository repoRepository) {
        return new JpaGithubRepositoryService(repoRepository);
    }

    @Bean
    public GithubCommitService githubCommitService(JpaGithubCommitRepository commitRepository) {
        return new JpaGithubCommitService(commitRepository);
    }

    @Bean
    public GithubPullService githubPullService(JpaGithubPullRepository pullRepository) {
        return new JpaGithubPullService(pullRepository);
    }

    @Bean
    public GithubReleaseService githubReleaseService(JpaGithubReleaseRepository releaseRepository) {
        return new JpaGithubReleaseService(releaseRepository);
    }
}

package edu.java.configuration;

import edu.java.client.Client;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.domain.jooq.JooqGithubCommitRepository;
import edu.java.scrapper.domain.jooq.JooqGithubPullRepository;
import edu.java.scrapper.domain.jooq.JooqGithubReleaseRepository;
import edu.java.scrapper.domain.jooq.JooqGithubRepoRepository;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import edu.java.service.ChatService;
import edu.java.service.GithubCommitService;
import edu.java.service.GithubPullService;
import edu.java.service.GithubReleaseService;
import edu.java.service.GithubRepositoryService;
import edu.java.service.LinkService;
import edu.java.service.jooq.JooqChatService;
import edu.java.service.jooq.JooqGithubCommitService;
import edu.java.service.jooq.JooqGithubPullService;
import edu.java.service.jooq.JooqGithubReleaseService;
import edu.java.service.jooq.JooqGithubRepositoryService;
import edu.java.service.jooq.JooqLinkService;
import java.util.List;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    @Bean
    public ChatService chatService(JooqChatRepository chatRepository) {
        return new JooqChatService(chatRepository);
    }

    @Bean
    public LinkService linkService(JooqLinkRepository linkRepository, List<Client> availableClients) {
        return new JooqLinkService(linkRepository, availableClients);
    }

    @Bean
    public GithubRepositoryService githubRepositoryService(JooqGithubRepoRepository repoRepository) {
        return new JooqGithubRepositoryService(repoRepository);
    }

    @Bean
    public GithubCommitService githubCommitService(JooqGithubCommitRepository commitRepository) {
        return new JooqGithubCommitService(commitRepository);
    }

    @Bean
    public GithubPullService githubPullService(JooqGithubPullRepository pullRepository) {
        return new JooqGithubPullService(pullRepository);
    }

    @Bean
    public GithubReleaseService githubReleaseService(JooqGithubReleaseRepository releaseRepository) {
        return new JooqGithubReleaseService(releaseRepository);
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }
}

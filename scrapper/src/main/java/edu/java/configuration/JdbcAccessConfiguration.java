package edu.java.configuration;

import edu.java.client.Client;
import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcGithubCommitDao;
import edu.java.dao.JdbcGithubPullDao;
import edu.java.dao.JdbcGithubReleaseDao;
import edu.java.dao.JdbcGithubRepositoryDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.service.ChatService;
import edu.java.service.GithubCommitService;
import edu.java.service.GithubPullService;
import edu.java.service.GithubReleaseService;
import edu.java.service.GithubRepositoryService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcGithubCommitService;
import edu.java.service.jdbc.JdbcGithubPullService;
import edu.java.service.jdbc.JdbcGithubReleaseService;
import edu.java.service.jdbc.JdbcGithubRepositoryService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public ChatService chatService(JdbcChatDao jdbcChatDao) {
        return new JdbcChatService(jdbcChatDao);
    }

    @Bean
    public LinkService linkService(JdbcLinkDao jdbcLinkDao, List<Client> availableClients) {
        return new JdbcLinkService(jdbcLinkDao, availableClients);
    }

    @Bean
    public GithubRepositoryService githubRepositoryService(JdbcGithubRepositoryDao repositoryDao) {
        return new JdbcGithubRepositoryService(repositoryDao);
    }

    @Bean
    public GithubCommitService githubCommitService(JdbcGithubCommitDao commitDao) {
        return new JdbcGithubCommitService(commitDao);
    }

    @Bean
    public GithubPullService githubPullService(JdbcGithubPullDao pullDao) {
        return new JdbcGithubPullService(pullDao);
    }

    @Bean
    public GithubReleaseService githubReleaseService(JdbcGithubReleaseDao releaseDao) {
        return new JdbcGithubReleaseService(releaseDao);
    }
}

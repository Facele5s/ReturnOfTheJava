package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @NotNull
    @Value("${client.base-url.github:https://api.github.com}")
    String gitHubBaseUrl;

    @NotNull
    @Value("${client.base-url.stackoverflow:https://api.stackexchange.com/2.3}")
    String stackOverFlowBaseUrl;

    @Bean("githubClient")
    public WebClient getGitHubClient() {
        return WebClient
            .builder()
            .baseUrl(gitHubBaseUrl)
            .build();
    }

    @Bean("stackoverflowClient")
    public WebClient getStackOverFlowClient() {
        return WebClient
            .builder()
            .baseUrl(stackOverFlowBaseUrl)
            .build();
    }
}

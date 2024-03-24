package edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @NotNull
    @Value("${client.base-url.scrapper:https://localhost}")
    String scrapperBaseUrl;

    @Bean("scrapperWebClient")
    public WebClient getScrapperClient() {
        return WebClient
            .builder()
            .baseUrl(scrapperBaseUrl)
            .build();
    }
}

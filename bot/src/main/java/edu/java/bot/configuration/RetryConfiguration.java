package edu.java.bot.configuration;

import edu.java.retry.RetryTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import static edu.java.bot.configuration.ApplicationConfig.Retry;

@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {
    private final ApplicationConfig config;

    @Bean
    public RetryTemplate retryTemplate() {
        Retry retry = config.retry();

        return switch (retry.type()) {
            case CONSTANT -> RetryTemplates.constantTemplate(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis()
            );
            case LINEAR -> RetryTemplates.linearTemplate(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis(),
                retry.config().maxIntervalMillis()
            );
            case EXPONENTIAL -> RetryTemplates.exponentialTemplate(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis(),
                retry.config().multiplier(),
                retry.config().maxIntervalMillis()
            );
        };
    }
}

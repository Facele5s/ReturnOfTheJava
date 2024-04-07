package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotNull
    Retry retry,
    KafkaConfig kafkaConfig
) {
    public record Retry(
        Set<Integer> httpStatuses,
        Integer maxAttempts,
        RetryType type,
        RetryConfig config
    ) {
        public enum RetryType {
            CONSTANT, LINEAR, EXPONENTIAL
        }

        public record RetryConfig(
            Long initialIntervalMillis,
            Long maxIntervalMillis,
            Double multiplier
        ) {
        }
    }

    public record KafkaConfig(
        String bootstrapServers,
        String group,
        TopicConfig topicConfig
    ) {
        public record TopicConfig(
            String name
        ) {

        }

    }
}

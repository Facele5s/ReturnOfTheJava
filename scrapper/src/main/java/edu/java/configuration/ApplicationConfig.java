package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @NotNull
    AccessType databaseAccessType,
    @NotEmpty
    String gitHubToken,
    @NotNull
    Retry retry,
    KafkaConfig kafkaConfig,
    Boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

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
        TopicConfig topicConfig
    ) {
        public record TopicConfig(
            String name,
            Integer partitions,
            Integer replicas
        ) {

        }
    }
}

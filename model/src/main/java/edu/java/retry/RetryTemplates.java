package edu.java.retry;

import java.util.Set;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@UtilityClass
public class RetryTemplates {
    public static RetryTemplate constantTemplate(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long period
    ) {
        RetryTemplate template = new RetryTemplate();
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(period);

        template.setBackOffPolicy(backOffPolicy);
        template.setRetryPolicy(new HttpRetryPolicy(retryStatuses, maxAttempts));

        return template;
    }

    public static RetryTemplate linearTemplate(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long initialInterval,
        Long maxInterval
    ) {
        RetryTemplate template = new RetryTemplate();
        LinearBackOffPolicy backOffPolicy = new LinearBackOffPolicy();
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMaxInterval(maxInterval);

        template.setBackOffPolicy(backOffPolicy);
        template.setRetryPolicy(new HttpRetryPolicy(retryStatuses, maxAttempts));

        return template;
    }

    public static RetryTemplate exponentialTemplate(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long initialInterval,
        Double multiplier,
        Long maxInterval
    ) {
        RetryTemplate template = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMultiplier(multiplier);
        backOffPolicy.setMaxInterval(maxInterval);

        template.setBackOffPolicy(backOffPolicy);
        template.setRetryPolicy(new HttpRetryPolicy(retryStatuses, maxAttempts));

        return template;
    }

    @Setter
    public class LinearBackOffPolicy implements BackOffPolicy {
        private Long initialInterval;
        private Long maxInterval;

        @Override
        public BackOffContext start(RetryContext context) {
            return new RetryTemplates.LinearBackOffPolicy.LinearBackOffContext();
        }

        @Override
        @SneakyThrows
        public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
            Long interval = (((LinearBackOffPolicy.LinearBackOffContext) backOffContext).attempts + 1)
                * initialInterval;

            Thread.sleep(Math.min(maxInterval, interval));
        }

        static class LinearBackOffContext implements BackOffContext {
            private Integer attempts = 0;
        }
    }
}

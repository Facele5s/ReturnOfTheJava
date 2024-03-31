package edu.java.retry;

import java.util.Set;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpClientErrorException;

public class HttpRetryPolicy extends SimpleRetryPolicy {
    private final Set<Integer> statuses;

    public HttpRetryPolicy(Set<Integer> statuses, Integer maxAttempts) {
        super(maxAttempts);
        this.statuses = statuses;
    }

    public boolean canRetry(RetryContext context) {
        if (context.getLastThrowable() instanceof HttpClientErrorException e) {
            return statuses.contains(e.getStatusCode().value());
        }

        return super.canRetry(context);
    }
}

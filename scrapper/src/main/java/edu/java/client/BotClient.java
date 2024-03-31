package edu.java.client;

import edu.java.dto.exception.ApiErrorResponseException;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotClient {
    private final WebClient webClient;

    @Autowired
    private RetryTemplate retryTemplate;

    private static final String ENDPOINT_UPDATES = "/updates";

    public BotClient(@Qualifier("botWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public LinkUpdateRequest sendUpdate(LinkUpdateRequest request) {
        return retryTemplate.execute(context -> webClient
            .post()
            .uri(ENDPOINT_UPDATES)
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(LinkUpdateRequest.class).block());
    }
}

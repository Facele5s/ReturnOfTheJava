package edu.java.client;

import edu.java.dto.exception.ApiErrorResponseException;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {
    private final WebClient webClient;

    private static final String DEFAULT_BASE_URL = "localhost";

    public BotClient(String baseUrl) {
        String url = baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public LinkUpdateRequest sendUpdate(LinkUpdateRequest request) {
        return webClient
            .post()
            .uri("/updates")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(LinkUpdateRequest.class).block();
    }
}

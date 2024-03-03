package edu.java.client;

import edu.java.dto.ApiErrorResponse;
import edu.java.dto.LinkUpdate;
import edu.java.exception.ApiErrorResponseException;
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

    public LinkUpdate sendUpdate(LinkUpdate request) {
        return webClient
            .post()
            .uri("/updates")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(LinkUpdate.class).block();
    }
}

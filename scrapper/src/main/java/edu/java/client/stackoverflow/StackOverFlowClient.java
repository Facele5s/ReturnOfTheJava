package edu.java.client.stackoverflow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverFlowClient {
    private final WebClient webClient;

    public StackOverFlowClient(@Qualifier("stackoverflowWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverFlowResponse getResponse(long questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class)
            .block();
    }
}

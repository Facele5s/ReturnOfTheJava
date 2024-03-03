package edu.java.bot.client;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.ApiErrorResponse;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.dto.ListLinkResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import edu.java.bot.exception.ApiErrorResponseException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperClient {
    private final WebClient webClient;

    private static final String DEFAULT_BASE_URL = "localhost";
    private static final String ENDPOINT_TG_CHAT = "/tg-chat/{id}";
    private static final String ENDPOINT_LINKS = "links";

    private static final String HEADER_TG_CHAT_ID = "Tg-chat-id";

    public ScrapperClient(String baseUrl) {
        String url = baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public void registerChat(Long id) {
        webClient
            .post()
            .uri(ENDPOINT_TG_CHAT + id)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            ).bodyToMono(String.class).block();
    }

    public void deleteChat(Long id) {
        webClient
            .delete()
            .uri(ENDPOINT_TG_CHAT + id)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(String.class).block();
    }

    public ListLinkResponse getLinks(Long id) {
        return webClient
            .get()
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(ListLinkResponse.class).block();
    }

    public LinkResponse addLink(Long id, AddLinkRequest request) {
        return webClient
            .post()
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(LinkResponse.class).block();
    }

    public LinkResponse deleteLink(Long id, RemoveLinkRequest request) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .bodyToMono(LinkResponse.class).block();
    }

}

package edu.java.bot.client;

import edu.java.dto.exception.ApiErrorResponseException;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ScrapperClient {
    private final WebClient webClient;

    private static final String ENDPOINT_TG_CHAT = "/tg-chat/{id}";
    private static final String ENDPOINT_LINKS = "/links";

    private static final String HEADER_TG_CHAT_ID = "Tg-Chat-id";

    public ScrapperClient(@Qualifier("scrapperWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public void registerChat(Long chatId) {
        webClient
            .post()
            .uri(ENDPOINT_TG_CHAT + chatId)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            ).bodyToMono(String.class).block();
    }

    public void deleteChat(Long chatId) {
        webClient
            .delete()
            .uri(ENDPOINT_TG_CHAT + chatId)
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

    public ListLinkResponse getLinks(Long chatId) {
        return webClient
            .get()
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(chatId))
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

    public LinkResponse addLink(Long chatId, AddLinkRequest request) {
        return webClient
            .post()
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(chatId))
            .bodyValue(request)
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

    public LinkResponse deleteLink(Long chatId, RemoveLinkRequest request) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(ENDPOINT_LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(chatId))
            .bodyValue(request)
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

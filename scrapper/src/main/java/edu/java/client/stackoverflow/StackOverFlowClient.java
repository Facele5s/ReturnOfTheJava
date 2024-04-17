package edu.java.client.stackoverflow;

import edu.java.client.Client;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@SuppressWarnings("MagicNumber")
public class StackOverFlowClient implements Client {
    private static final String URL_PATTERN = "^((http)s?://)?stackoverflow\\.com/questions/(\\d+)/[\\w-]?$";

    private final WebClient webClient;

    public StackOverFlowClient(@Qualifier("stackoverflowWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverFlowResponse getResponse(URI url) {
        Matcher matcher = Pattern.compile(URL_PATTERN).matcher(url.toString());
        matcher.matches();

        long questionId = Long.parseLong(matcher.group(3));

        return webClient
            .get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class)
            .block();
    }

    @Override
    public boolean isLinkSupported(URI url) {
        return url.toString().matches(URL_PATTERN);
    }
}

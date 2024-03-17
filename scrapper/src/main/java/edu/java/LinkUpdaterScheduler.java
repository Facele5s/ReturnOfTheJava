package edu.java;

import edu.java.client.BotClient;
import edu.java.client.UrlClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.entity.Chat;
import edu.java.entity.ClientResponse;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final ApplicationConfig config;
    private final BotClient botClient;
    private final LinkService linkService;
    private final ChatService chatService;
    private final List<UrlClient> clients;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() throws Exception {
        log.info("Checking for updates");

        linkService.getLongUncheckedLinks(config.scheduler().forceCheckDelay()).forEach(link -> {
            List<UrlClient> client = clients.stream().filter(c -> c.supportsUrl(link.getUrl())).toList();

            if (client.isEmpty()) {
                log.error("The link is not supported: " + link.getUrl());
                return;
            }

            ClientResponse clientResponse = client.getFirst().fetch(link.getUrl());

            try {
                if (clientResponse.updatedAt().isAfter(link.getUpdatedAt())) {
                    linkService.setLastUpdateDate(link.getId(), clientResponse.updatedAt());

                    var chats = chatService.getChatByLink(link.getId());
                    botClient.sendUpdate(new LinkUpdateRequest(
                        link.getId(),
                        link.getUrl(),
                        "",
                        chats.stream().map(Chat::getId).toList()
                    ));
                }
            } catch (NotFoundException e) {
                log.error(e.getDescription());
            }

        });
    }
}

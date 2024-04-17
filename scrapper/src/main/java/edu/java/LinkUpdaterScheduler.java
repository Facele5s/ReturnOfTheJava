package edu.java;

import edu.java.client.Client;
import edu.java.client.Response;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.entity.Chat;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdateService;
import java.time.OffsetDateTime;
import java.util.Collection;
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
    private final LinkUpdateService updateService;

    private final LinkService linkService;
    private final ChatService chatService;

    private final List<Client> availableClients;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Checking for updates");

        linkService.getLongUncheckedLinks(config.scheduler().forceCheckDelay()).forEach(link -> {
            Client client = availableClients
                .stream()
                .filter(c -> c.isLinkSupported(link.getUrl()))
                .findFirst().orElse(null);

            if (client == null) {
                log.error("The link is not supported: " + link.getUrl());
                return;
            }

            Response response = client.getResponse(link.getUrl());

            try {
                linkService.setLastCheckDate(link.getId(), OffsetDateTime.now());

                if (response.getUpdateDate() != null && response.getUpdateDate().isAfter(link.getUpdatedAt())) {
                    linkService.setLastUpdateDate(link.getId(), response.getUpdateDate());

                    Collection<Chat> chats = chatService.getChatsByLink(link.getId());
                    LinkUpdateRequest updateRequest = new LinkUpdateRequest(
                        link.getId(),
                        link.getUrl(),
                        response.getDescription(),
                        chats.stream().map(Chat::getId).toList()
                    );

                    updateService.sendUpdate(updateRequest);
                }
            } catch (NullPointerException e) {
                log.error(e.getMessage());
            } catch (NotFoundException e) {
                log.error(e.getDescription());
            }
        });
    }
}

package edu.java.service;

import edu.java.client.BotClient;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScrapperHttpUpdateSender implements LinkUpdateService {
    private final BotClient botClient;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        botClient.sendUpdate(request);
    }
}

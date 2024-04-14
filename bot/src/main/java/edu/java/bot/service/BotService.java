package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ObserverBot;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.request.LinkUpdateRequest;
import io.micrometer.core.instrument.Counter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {
    private final ObserverBot observerBot;
    private final Counter processedUpdatesCounter;

    private final Map<Long, LinkUpdateRequest> updatesByIds = new HashMap<>();

    private static final String MSG_UPD_EXISTS = "Update already exists";
    private static final String DESC_UPD_TWICE = "You can't get the same update twice";

    public void sendNotification(LinkUpdateRequest request) throws BadRequestException {
        if (updatesByIds.containsKey(request.id())) {
            throw new BadRequestException(
                MSG_UPD_EXISTS,
                DESC_UPD_TWICE
            );
        }

        request.tgChatIds().forEach(chatId -> {
            String messageText = String.format(
                "The link content was updated! \n%s\n%s",
                request.url(),
                request.description()
            );

            observerBot.sendMessageToChat(new SendMessage(chatId, messageText));
        });

        processedUpdatesCounter.increment();
    }
}

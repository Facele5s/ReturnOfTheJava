package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ObserverBot;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {
    private final ObserverBot observerBot;

    public void sendNotification(LinkUpdateRequest request) {
        request.tgChatIds().forEach(chatId -> {
            String messageText = String.format(
                "The link content was updated! \n%s\n%s",
                request.url(),
                request.description()
            );

            observerBot.sendMessageToChat(new SendMessage(chatId, messageText));
        });
    }
}

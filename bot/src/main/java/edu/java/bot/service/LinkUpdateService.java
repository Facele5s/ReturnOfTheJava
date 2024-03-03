package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ObserverBot;
import edu.java.bot.dto.LinkUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {
    private final ObserverBot observerBot;

    public void sendNotification(LinkUpdate request) {
        request.tgChatIds().forEach(chatId -> {
            observerBot.sendMessageToChat(new SendMessage(
                chatId,
                String.format("The link content was updated! \n%s\n%s",
                    request.url(),
                    request.description())
            ));
        });
    }
}

package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Registers a user";
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.message().chat().id();

        scrapperClient.registerChat(chatId);

        return new SendMessage(chatId, "Hello! I'm link observer bot!\n"
            + "Use /help to get information about commands");
    }
}

package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
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

        return new SendMessage(chatId, "Hello! I'm link observer bot!\n"
            + "Use /help to get information about commands");
    }
}

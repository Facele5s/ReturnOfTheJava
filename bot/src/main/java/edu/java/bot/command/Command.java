package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    default boolean commandMatches(Update update) {
        return update.message().text().trim().split(" ")[0].equals(command());
    }

    default BotCommand convert() {
        return new BotCommand(command(), description());
    }

    SendMessage respond(Update update);
}

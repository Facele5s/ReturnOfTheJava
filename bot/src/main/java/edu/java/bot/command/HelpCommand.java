package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelpCommand implements Command {

    private List<Command> commandList;

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Shows information about bot commands";
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.message().chat().id();

        return new SendMessage(chatId, generateCommandsHelp());
    }

    private String generateCommandsHelp() {
        StringBuilder text = new StringBuilder("Commands:\n");

        commandList.forEach(c -> text.append(c.command())
            .append(" - ")
            .append(c.description())
            .append("\n"));
        text.deleteCharAt(text.length() - 1);

        return text.toString();
    }
}

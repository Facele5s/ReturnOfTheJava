package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackCommand implements Command {

    private List<String> linksList;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Starts tracking the link";
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.message().chat().id();

        String text;
        if (argumentsCorrect(update)) {
            String link = update.message().text().trim().split(" ")[1];

            text = addLink(link);
        } else {
            text = "The link is incorrect!";
        }

        return new SendMessage(chatId, text);
    }

    private boolean argumentsCorrect(Update update) {
        String arguments = update.message().text();

        return arguments.matches("^/track \\S+$");
    }

    private String addLink(String link) {
        if (linksList.contains(link)) {
            return "The link is already observed.";
        }

        linksList.add(link);
        return "The link is observed now:\n" + link;
    }
}

package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackCommand implements Command {

    private List<String> linksList;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stops tracking the link";
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.message().chat().id();

        String text;
        if (argumentsCorrect(update)) {
            String link = update.message().text().trim().split(" ")[1];

            text = removeLink(link);
        } else {
            text = "The link is incorrect!";
        }

        return new SendMessage(chatId, text);
    }

    private boolean argumentsCorrect(Update update) {
        String arguments = update.message().text();

        return arguments.matches("^/untrack \\S+$");
    }

    private String removeLink(String link) {
        if (!linksList.contains(link)) {
            return "The link is no such observed link.";
        }

        linksList.remove(link);
        return "The link is not observed now:\n" + link;
    }
}

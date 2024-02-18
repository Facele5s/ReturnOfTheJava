package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ListCommand implements Command {

    private List<String> linksList;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Shows list of observed links";
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.message().chat().id();

        return new SendMessage(chatId, generateLinksText());
    }

    private String generateLinksText() {
        if (linksList.isEmpty()) {
            return "There are no observed links.";
        }

        StringBuilder text = new StringBuilder("Observed links:\n");
        linksList.forEach(l -> text.append(l).append("\n"));
        text.deleteCharAt(text.length() - 1);

        return text.toString();
    }
}

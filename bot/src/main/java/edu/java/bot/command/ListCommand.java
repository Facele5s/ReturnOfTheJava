package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.dto.response.LinkResponse;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    private List<URI> linksList;

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

        linksList = scrapperClient.getLinks(chatId).block()
            .links().stream()
            .map(LinkResponse::url)
            .toList();

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

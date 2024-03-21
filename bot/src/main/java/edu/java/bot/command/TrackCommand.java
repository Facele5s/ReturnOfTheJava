package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.LinkResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    private List<URI> linksList;

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
            URI link = URI.create(update.message().text().trim().split(" ")[1]);

            linksList = scrapperClient.getLinks(chatId).block()
                .links().stream()
                .map(LinkResponse::url)
                .toList();

            text = addLink(chatId, link);
        } else {
            text = "The link is incorrect!";
        }

        return new SendMessage(chatId, text);
    }

    private boolean argumentsCorrect(Update update) {
        String arguments = update.message().text();

        return arguments.matches("^/track \\S+$");
    }

    private String addLink(Long chatId, URI link) {
        if (linksList.contains(link)) {
            return "The link is already observed.";
        }

        AddLinkRequest request = new AddLinkRequest(link);
        scrapperClient.addLink(chatId, request);

        return "The link is observed now:\n" + link;
    }
}

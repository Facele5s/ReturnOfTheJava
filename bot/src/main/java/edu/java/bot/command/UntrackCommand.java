package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    private List<URI> linksList;

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
            URI link = URI.create(update.message().text().trim().split(" ")[1]);

            linksList = scrapperClient.getLinks(chatId)
                .links().stream()
                .map(LinkResponse::url)
                .toList();

            text = removeLink(chatId, link);
        } else {
            text = "The link is incorrect!";
        }

        return new SendMessage(chatId, text);
    }

    private boolean argumentsCorrect(Update update) {
        String arguments = update.message().text();

        return arguments.matches("^/untrack \\S+$");
    }

    private String removeLink(Long chatId, URI link) {
        if (!linksList.contains(link)) {
            return "There is no such observed link.";
        }

        RemoveLinkRequest request = new RemoveLinkRequest(link);
        scrapperClient.deleteLink(chatId, request);

        return "The link is not observed now:\n" + link;
    }
}

package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.command.Command;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandsTest {
    private final List<String> linksList = List.of(
        "link1",
        "link2",
        "link3"
    );

    private final List<Command> commandsList = List.of(
        new StartCommand(),
        new HelpCommand(List.of(new StartCommand())),
        new ListCommand(linksList),
        new TrackCommand(linksList),
        new UntrackCommand(linksList)
    );

    @Test
    @DisplayName("Start command test")
    public void startTest() {
        ObserverBot bot = new ObserverBot(null, commandsList, new ArrayList<>());

        String command = "/start";
        String responseText = getReply(bot, command);
        assertEquals("Hello! I'm link observer bot!\n"
            + "Use /help to get information about commands", responseText);
    }

    @Test
    @DisplayName("Help command test")
    public void helpTest() {
        ObserverBot bot = new ObserverBot(null, commandsList, new ArrayList<>());

        String command = "/help";
        String responseText = getReply(bot, command);
        assertEquals("Commands:\n" +
            "/start - Registers a user", responseText);
    }

    @Test
    @DisplayName("List command test")
    public void listTest() {
        ObserverBot bot = new ObserverBot(null, commandsList, linksList);

        String command = "/list";
        String responseText = getReply(bot, command);
        assertEquals("Observed links:\n" +
            "link1\n" +
            "link2\n" +
            "link3", responseText);
    }

    @Test
    @DisplayName("Track command test")
    public void trackTest() {
        List<String> links = new ArrayList<>();
        Command customTrackCommand = new TrackCommand(links);
        ObserverBot bot = new ObserverBot(null, List.of(customTrackCommand), null);

        String command = "/track re4l_l1nk";
        String responseText = getReply(bot, command);
        assertEquals("The link is observed now:\n" +
            "re4l_l1nk", responseText);
        assertTrue(links.contains("re4l_l1nk"));

        responseText = getReply(bot, command);
        assertEquals("The link is already observed.", responseText);

        command = "/track";
        responseText = getReply(bot, command);
        assertEquals("The link is incorrect!", responseText);
    }

    @Test
    @DisplayName("Untrack command test")
    public void untrackTest() {
        List<String> links = new ArrayList<>();
        links.add("re4l_l1nk");
        Command customTrackCommand = new UntrackCommand(links);
        ObserverBot bot = new ObserverBot(null, List.of(customTrackCommand), null);

        String command = "/untrack re4l_l1nk";
        String responseText = getReply(bot, command);
        assertEquals("The link is not observed now:\n" +
            "re4l_l1nk", responseText);


        responseText = getReply(bot, command);
        assertEquals("There is no such observed link.", responseText);

        command = "/untrack";
        responseText = getReply(bot, command);
        assertEquals("The link is incorrect!", responseText);
    }

    @Test
    @DisplayName("Incorrect command test")
    public void incorrectTest() {
        ObserverBot bot = new ObserverBot(null, commandsList, null);

        String command = "/begin";
        String responseText = getReply(bot, command);
        assertEquals("The command is incorrect!", responseText);

        command = "start";
        responseText = getReply(bot, command);
        assertEquals("The command is incorrect!", responseText);
    }

    private String getReply(ObserverBot bot, String command) {
        String updStr = "{\"message\":{\"chat\":{\"id\":0},\"text\":\"" + command + "\"}}";
        Update update = BotUtils.parseUpdate(updStr);
        SendMessage response = bot.processMessage(update);

        return response.getParameters().get("text").toString();
    }
}

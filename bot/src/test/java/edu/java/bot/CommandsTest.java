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

public class CommandsTest {
    private final List<String> basicLinksList = List.of(
        "link1",
        "link2",
        "link3"
    );

    private final List<Command> basicCommandsList = List.of(
        new StartCommand(),
        new HelpCommand(List.of(new StartCommand())),
        new ListCommand(basicLinksList),
        new TrackCommand(basicLinksList),
        new UntrackCommand(basicLinksList)
    );

    @Test
    @DisplayName("Start command")
    public void startTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String command = "/start";

        String responseText = getReply(bot, command);

        assertEquals("Hello! I'm link observer bot!\n"
            + "Use /help to get information about commands", responseText);
    }

    @Test
    @DisplayName("Help command")
    public void helpTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String command = "/help";

        String responseText = getReply(bot, command);

        assertEquals("Commands:\n" +
            "/start - Registers a user", responseText);
    }

    @Test
    @DisplayName("List command")
    public void listTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String command = "/list";

        String responseText = getReply(bot, command);

        assertEquals("Observed links:\n" +
            "link1\n" +
            "link2\n" +
            "link3", responseText);
    }

    @Test
    @DisplayName("Track command correct")
    public void trackTest() {
        List<Command> customCommandList = List.of(new TrackCommand(new ArrayList<>()));
        ObserverBot bot = getBot(customCommandList);
        String command = "/track re4l_l1nk";

        String responseText = getReply(bot, command);

        assertEquals("The link is observed now:\n" +
            "re4l_l1nk", responseText);
    }

    @Test
    @DisplayName("Track command with repeated link addition")
    public void trackRepeatTest() {
        List<String> customLinksList = new ArrayList<>();
        customLinksList.add("re4l_l1nk");
        List<Command> customCommandList = List.of(new TrackCommand(customLinksList));
        ObserverBot bot = getBot(customCommandList);
        String command = "/track re4l_l1nk";
        getReply(bot, command);

        String responseText = getReply(bot, command);

        assertEquals("The link is already observed.", responseText);
    }

    @Test
    @DisplayName("Track command with invalid link")
    public void trackInvalidTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String commandIncorrect = "/track";

        String responseText = getReply(bot, commandIncorrect);

        assertEquals("The link is incorrect!", responseText);
    }

    @Test
    @DisplayName("Untrack command correct")
    public void untrackTest() {
        List<String> customLinksList = new ArrayList<>();
        customLinksList.add("re4l_l1nk");
        List<Command> customCommandList = List.of(new UntrackCommand(customLinksList));
        ObserverBot bot = getBot(customCommandList);
        String command = "/untrack re4l_l1nk";

        String responseText = getReply(bot, command);

        assertEquals("The link is not observed now:\n" +
            "re4l_l1nk", responseText);
    }

    @Test
    @DisplayName("Untrack command with unexisted link")
    public void untrackUnexistedLinkTest() {
        List<String> customLinksList = new ArrayList<>();
        List<Command> customCommandList = List.of(new UntrackCommand(customLinksList));
        ObserverBot bot = getBot(customCommandList);
        String command = "/untrack re4l_l1nk";

        String responseText = getReply(bot, command);

        assertEquals("There is no such observed link.", responseText);
    }

    @Test
    @DisplayName("Untrack command with ivalid link")
    public void untrackInvalidTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String commandIncorrect = "/untrack";

        String responseText = getReply(bot, commandIncorrect);

        assertEquals("The link is incorrect!", responseText);
    }

    @Test
    @DisplayName("Incorrect command")
    public void incorrectTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String incorrectCommand1 = "/begin";
        String incorrectCommand2 = "start";

        String responseText1 = getReply(bot, incorrectCommand1);
        String responseText2 = getReply(bot, incorrectCommand2);

        assertEquals("The command is incorrect!", responseText1);
        assertEquals("The command is incorrect!", responseText2);
    }

    private String getReply(ObserverBot bot, String command) {
        String updStr = "{\"message\":{\"chat\":{\"id\":0},\"text\":\"" + command + "\"}}";
        Update update = BotUtils.parseUpdate(updStr);
        SendMessage response = bot.parseCommand(update);

        return response.getParameters().get("text").toString();
    }

    private ObserverBot getBot(List<Command> commandsList) {
        return new ObserverBot(null, commandsList, basicLinksList);
    }
}

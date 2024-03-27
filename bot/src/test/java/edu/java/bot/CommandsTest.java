package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import java.net.URI;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class CommandsTest {
    @Spy
    private ScrapperClient scrapperClient;

    private Update updatePlug;
    private Message messagePlug;
    private Chat chatPlug;
    private Long chatIdPlug;

    private final List<Command> basicCommandsList = List.of(
        new StartCommand(scrapperClient),
        new HelpCommand(List.of(new StartCommand(scrapperClient))),
        new ListCommand(scrapperClient),
        new TrackCommand(scrapperClient),
        new UntrackCommand(scrapperClient)
    );

    @BeforeEach
    public void setup() {
        updatePlug = Mockito.spy(new Update());
        messagePlug = Mockito.spy(new Message());
        chatPlug = Mockito.spy(new Chat());
        chatIdPlug = 100500L;
        scrapperClient = Mockito.spy(new ScrapperClient(null));

        when(updatePlug.message()).thenReturn(messagePlug);
        when(messagePlug.chat()).thenReturn(chatPlug);
        when(chatPlug.id()).thenReturn(chatIdPlug);
    }

    @Test
    @DisplayName("Start command")
    public void startTest() {
        //Arrange
        doNothing().when(scrapperClient).registerChat(chatIdPlug);
        StartCommand command = new StartCommand(scrapperClient);
        String expectedText = "Hello! I'm link observer bot!\n" +
            "Use /help to get information about commands";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedText, toString(response));
    }

    @Test
    @DisplayName("Help command")
    public void helpTest() {
        //Arrange
        List<Command> commandList = List.of(new StartCommand(scrapperClient));
        HelpCommand command = new HelpCommand(commandList);
        String expectedResponse = "Commands:\n" +
            "/start - Registers a user";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("List command")
    public void listTest() {
        //Arrange
        URI url = URI.create("link1");
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(chatIdPlug, url));
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        doReturn(listLinkResponse).when(scrapperClient).getLinks(chatIdPlug);
        ListCommand command = new ListCommand(scrapperClient);
        String expectedResponse = "Observed links:\n" +
            "link1";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Track command correct")
    public void trackTest() {
        //Arrange
        URI url = URI.create("re4l_l1nk");
        List<LinkResponse> linkResponseList = List.of();
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 0);
        doReturn("/track re4l_l1nk").when(messagePlug).text();
        doReturn(listLinkResponse).when(scrapperClient).getLinks(chatIdPlug);
        AddLinkRequest request = new AddLinkRequest(url);
        doReturn(null).when(scrapperClient).addLink(chatIdPlug, request);
        TrackCommand command = new TrackCommand(scrapperClient);
        String expectedResponse = "The link is observed now:\n" +
            "re4l_l1nk";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Track command with repeated link addition")
    public void trackRepeatTest() {
        //Arrange
        URI url = URI.create("re4l_l1nk");
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(chatIdPlug, url));
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        doReturn("/track re4l_l1nk").when(messagePlug).text();
        doReturn(listLinkResponse).when(scrapperClient).getLinks(chatIdPlug);
        TrackCommand command = new TrackCommand(scrapperClient);
        String expectedResponse = "The link is already observed.";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Track command with invalid link")
    public void trackInvalidTest() {
        //Arrange
        doReturn("/track").when(messagePlug).text();
        TrackCommand command = new TrackCommand(scrapperClient);
        String expectedResponse = "The link is incorrect!";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Untrack command correct")
    public void untrackTest() {
        //Arrange
        URI url = URI.create("re4l_l1nk");
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(chatIdPlug, url));
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        doReturn("/untrack re4l_l1nk").when(messagePlug).text();
        doReturn(listLinkResponse).when(scrapperClient).getLinks(chatIdPlug);
        RemoveLinkRequest request = new RemoveLinkRequest(url);
        doReturn(null).when(scrapperClient).deleteLink(chatIdPlug, request);
        UntrackCommand command = new UntrackCommand(scrapperClient);
        String expectedResponse = "The link is not observed now:\n" +
            "re4l_l1nk";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Untrack command with unexisted link")
    public void untrackUnexistedLinkTest() {
        //Arrange
        List<LinkResponse> linkResponseList = List.of();
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 0);
        doReturn("/untrack re4l_l1nk").when(messagePlug).text();
        doReturn(listLinkResponse).when(scrapperClient).getLinks(chatIdPlug);
        UntrackCommand command = new UntrackCommand(scrapperClient);
        String expectedResponse = "There is no such observed link.";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Untrack command with ivalid link")
    public void untrackInvalidTest() {
        //Arrange
        doReturn("/untrack").when(messagePlug).text();
        UntrackCommand command = new UntrackCommand(scrapperClient);
        String expectedResponse = "The link is incorrect!";

        //Act
        SendMessage response = command.respond(updatePlug);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    @Test
    @DisplayName("Incorrect command")
    public void incorrectTest() {
        //Arrange
        ObserverBot bot = new ObserverBot(null, basicCommandsList);
        String incorrectCommand = "/begin";
        String updStr = "{\"message\":{\"chat\":{\"id\":100500},\"text\":\"" + incorrectCommand + "\"}}";
        Update update = BotUtils.parseUpdate(updStr);
        String expectedResponse = "The command is incorrect!";

        //Act
        SendMessage response = bot.parseCommand(update);

        //Assert
        assertEquals(expectedResponse, toString(response));
    }

    private String toString(SendMessage message) {
        return message.getParameters().get("text").toString();
    }
}

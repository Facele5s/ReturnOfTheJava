package edu.java.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class CommandsTest {
    private final WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build();
    private final ScrapperClient scrapperClient = new ScrapperClient(webClient);
    private static WireMockServer wireMockServer;

    private final List<URI> basicLinksList = List.of(
        URI.create("link1"),
        URI.create("link2"),
        URI.create("link3")
    );

    private final List<Command> basicCommandsList = List.of(
        new StartCommand(scrapperClient),
        new HelpCommand(List.of(new StartCommand(scrapperClient))),
        new ListCommand(new ScrapperClient(null), basicLinksList),
        new TrackCommand(scrapperClient),
        new UntrackCommand(scrapperClient)
    );

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterAll
    public static void stop() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Start command")
    public void startTest() {
        ObserverBot bot = getBot(basicCommandsList);
        String command = "/start";
        String responseString = "Hello! I'm link observer bot!\n"
            + "Use /help to get information about commands";
        stubFor(post("/tg-chat/100500")
            .willReturn(aResponse()
                .withStatus(200)
                //.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //.withBody(responseString))
            ));


        verify(postRequestedFor(urlEqualTo("/tg-chat/100500")));
        //String responseText = getReply(bot, command);

        //assertEquals("Hello! I'm link observer bot!\n"
        //    + "Use /help to get information about commands", responseText);
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

        stubFor(get("/links/100500")
            .willReturn(aResponse()
                    .withStatus(200)
                //.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //.withBody(responseString))
            ));
        StepVerifier.create(scrapperClient.getLinks(100500L))
            .verifyComplete();

        verify(getRequestedFor(urlEqualTo("/links/100500")));


        String responseText = getReply(bot, command);

        assertEquals("Observed links:\n" +
            "link1\n" +
            "link2\n" +
            "link3", responseText);
    }

    @Test
    @DisplayName("Track command correct")
    public void trackTest() {
        List<Command> customCommandList = List.of(new TrackCommand(scrapperClient));
        ObserverBot bot = getBot(customCommandList);
        String command = "/track re4l_l1nk";

        String responseText = getReply(bot, command);

        assertEquals("The link is observed now:\n" +
            "re4l_l1nk", responseText);
    }

    @Test
    @DisplayName("Track command with repeated link addition")
    public void trackRepeatTest() {
        List<URI> customLinksList = new ArrayList<>();
        customLinksList.add(URI.create("re4l_l1nk"));
        List<Command> customCommandList = List.of(new TrackCommand(scrapperClient));
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
        List<URI> customLinksList = new ArrayList<>();
        customLinksList.add(URI.create("re4l_l1nk"));
        List<Command> customCommandList = List.of(new UntrackCommand(scrapperClient));
        ObserverBot bot = getBot(customCommandList);
        String command = "/untrack re4l_l1nk";

        String responseText = getReply(bot, command);

        assertEquals("The link is not observed now:\n" +
            "re4l_l1nk", responseText);
    }

    @Test
    @DisplayName("Untrack command with unexisted link")
    public void untrackUnexistedLinkTest() {
        List<URI> customLinksList = new ArrayList<>();
        List<Command> customCommandList = List.of(new UntrackCommand(scrapperClient));
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
        String updStr = "{\"message\":{\"chat\":{\"id\":100500},\"text\":\"" + command + "\"}}";
        Update update = BotUtils.parseUpdate(updStr);
        System.out.println(updStr);
        SendMessage response = bot.parseCommand(update);

        return response.getParameters().get("text").toString();
    }

    private ObserverBot getBot(List<Command> commandsList) {
        return new ObserverBot(null, commandsList);
    }
}

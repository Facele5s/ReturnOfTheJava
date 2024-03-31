package edu.java.scrapper;

import edu.java.controller.ScrapperController;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListChatResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.ScrapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ScrapperControllerTest {
    private static final Long CHAT_ID = 100500L;

    private ScrapperService scrapperService;
    private ScrapperController scrapperController;

    @Mock
    private ChatService chatService;

    @Mock
    private LinkService linkService;

    @BeforeEach
    public void setup() {
        scrapperService = Mockito.spy(new ScrapperService(chatService, linkService));
        scrapperController = Mockito.spy(new ScrapperController(scrapperService));
    }

    @Test
    @DisplayName("Chat registration")
    public void chatRegisterTest() throws Exception {
        //Arrange
        List<ChatResponse> chatResponseList = List.of();
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 0);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(chatService.registerChat(CHAT_ID)).thenReturn(new ChatResponse(CHAT_ID, null));

        //Act
        ResponseEntity<Void> responseEntity = scrapperController.registerChat(CHAT_ID);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Chat deleting")
    public void chatDeleteTest() throws Exception {
        //Arrange
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(chatService.deleteChat(CHAT_ID)).thenReturn(new ChatResponse(CHAT_ID, null));

        //Act
        ResponseEntity<Void> responseEntity = scrapperController.deleteChat(CHAT_ID);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Link adding")
    public void linkAddTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        List<LinkResponse> linkResponseList = List.of();
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 0);
        AddLinkRequest request = new AddLinkRequest(url);
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);
        when(linkService.add(CHAT_ID, url)).thenReturn(linkResponse);

        //Act
        ResponseEntity<LinkResponse> response = scrapperController.addLink(CHAT_ID, request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Link deleting")
    public void linkDeleteTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 0);
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(CHAT_ID, url));
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        RemoveLinkRequest request = new RemoveLinkRequest(url);
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);
        when(linkService.untrack(CHAT_ID, url)).thenReturn(linkResponse);

        //Act
        ResponseEntity<LinkResponse> response = scrapperController.deleteLink(CHAT_ID, request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Links getting")
    public void linkGetTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 0);
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(CHAT_ID, url));
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);

        //Act
        ResponseEntity<ListLinkResponse> response = scrapperController.getLinks(CHAT_ID);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

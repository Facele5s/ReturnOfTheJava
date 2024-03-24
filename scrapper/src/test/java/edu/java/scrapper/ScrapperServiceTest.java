package edu.java.scrapper;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
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
import java.net.URI;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ScrapperServiceTest {
    private static final Long CHAT_ID = 100500L;

    private ScrapperService service;

    @Mock
    private ChatService chatService;

    @Mock
    private LinkService linkService;

    @BeforeEach
    public void setup() {
        service = Mockito.spy(new ScrapperService(chatService, linkService));
    }

    @Test
    @DisplayName("Check for chat re-registration")
    public void registerChatTwiceTest() {
        //Arrange
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);

        //Act + Assert
        assertThrows(BadRequestException.class, () -> {
            service.registerChat(CHAT_ID);
        });
    }

    @Test
    @DisplayName("Check the chat for existence before deleting")
    public void deleteUnexistedChatTest() {
        //Arrange
        List<ChatResponse> chatResponseList = List.of();
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);

        //Act + Assert
        assertThrows(NotFoundException.class, () -> {
            service.deleteChat(CHAT_ID);
        });
    }

    @Test
    @DisplayName("Check for link re-adding")
    public void addLinkTwiceTest() {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        List<LinkResponse> linkResponseList = List.of(linkResponse);
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);

        //Act + Assert
        assertThrows(BadRequestException.class, () -> {
            service.addLink(linkResponse);
        });
    }

    @Test
    @DisplayName("Check the link for existence before deleting")
    public void deleteUnexistedLinkTest() {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        List<LinkResponse> linkResponseList = List.of();
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 0);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);

        //Act + Assert
        assertThrows(BadRequestException.class, () -> {
            service.deleteLink(linkResponse);
        });
    }

    @Test
    @DisplayName("Getting links check")
    public void getLinksTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        List<ChatResponse> chatResponseList = List.of(new ChatResponse(CHAT_ID, null));
        ListChatResponse listChatResponse = new ListChatResponse(chatResponseList, 1);
        List<LinkResponse> linkResponseList = List.of(linkResponse);
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        when(chatService.getAllChats()).thenReturn(listChatResponse);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);

        //Act
        ListLinkResponse response = service.getLinks(CHAT_ID);

        //Assert
        assertEquals(listLinkResponse, response);
    }
}

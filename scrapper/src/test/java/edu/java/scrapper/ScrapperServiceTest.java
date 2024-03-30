package edu.java.scrapper;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ScrapperServiceTest {
    private static final Long CHAT_ID = 100500L;
    private static final BadRequestException BAD_REQUEST = new BadRequestException(null, null);
    private static final NotFoundException NOT_FOUND = new NotFoundException(null, null);

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
    public void registerChatTwiceTest() throws Exception {
        //Arrange
        when(chatService.registerChat(CHAT_ID)).thenThrow(BAD_REQUEST);

        //Act + Assert
        assertThrows(BadRequestException.class, () -> {
            service.registerChat(CHAT_ID);
        });

        assertDoesNotThrow(() -> {
            service.registerChat(1L);
        });
    }

    @Test
    @DisplayName("Check the chat for existence before deleting")
    public void deleteUnexistedChatTest() throws Exception {
        //Arrange
        when(chatService.deleteChat(CHAT_ID)).thenThrow(NOT_FOUND);

        //Act + Assert
        assertThrows(NotFoundException.class, () -> {
            service.deleteChat(CHAT_ID);
        });
    }

    @Test
    @DisplayName("Check for link re-adding")
    public void addLinkTwiceTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        when(linkService.add(CHAT_ID, url)).thenThrow(BAD_REQUEST);

        //Act + Assert
        assertThrows(BadRequestException.class, () -> {
            service.addLink(linkResponse);
        });
    }

    @Test
    @DisplayName("Check the link for existence before deleting")
    public void deleteUnexistedLinkTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        when(linkService.untrack(CHAT_ID, url)).thenThrow(NOT_FOUND);

        //Act + Assert
        assertThrows(NotFoundException.class, () -> {
            service.deleteLink(linkResponse);
        });
    }

    @Test
    @DisplayName("Getting links check")
    public void getLinksTest() throws Exception {
        //Arrange
        URI url = URI.create("link");
        LinkResponse linkResponse = new LinkResponse(CHAT_ID, url);
        List<LinkResponse> linkResponseList = List.of(linkResponse);
        ListLinkResponse listLinkResponse = new ListLinkResponse(linkResponseList, 1);
        when(linkService.getLinksByChat(CHAT_ID)).thenReturn(listLinkResponse);

        //Act
        ListLinkResponse response = service.getLinks(CHAT_ID);

        //Assert
        assertEquals(listLinkResponse, response);
    }
}

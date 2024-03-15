package edu.java.scrapper;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.service.ScrapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScrapperServiceTest {
    private ScrapperService service;

    @BeforeEach
    public void serviceInit() throws Exception {
        this.service = new ScrapperService();

        service.registerChat(1L);
        service.registerChat(2L);

        service.addLink(1L, new LinkResponse(1L, new URI("link1")));
        service.addLink(1L, new LinkResponse(2L, new URI("link2")));
        service.addLink(1L, new LinkResponse(3L, new URI("link3")));
    }

    @Test
    @DisplayName("Check for chat re-registration")
    public void registerChatTwiceTest() {
        assertThrows(BadRequestException.class, () -> {
            service.registerChat(2L);
        });
    }

    @Test
    @DisplayName("Check the chat for existence before deleting ")
    public void deleteUnexistedChatTest() {
        assertThrows(NotFoundException.class, () -> {
            service.deleteChat(3L);
        });
    }

    @Test
    @DisplayName("Check for link re-adding")
    public void addLinkTwiceTest() {
        assertThrows(BadRequestException.class, () -> {
            service.addLink(1L, new LinkResponse(1L, new URI("link1")));
        });
    }

    @Test
    @DisplayName("Check the link for existence before deleting")
    public void deleteUnexistedLinkTest() {
        assertThrows(NotFoundException.class, () -> {
            service.deleteLink(1L, new LinkResponse(1000500L, new URI("link100500")));
        });
    }

    @Test
    @DisplayName("Getting links check")
    public void getLinksTest() throws Exception {
        //Arrange
        ListLinkResponse expectedLinksList = new ListLinkResponse(Arrays.asList(
            new LinkResponse(1L, new URI("link1")),
            new LinkResponse(2L, new URI("link2")),
            new LinkResponse(3L, new URI("link3"))
        ), 0);

        //Act + Assert
        assertThrows(NotFoundException.class, () -> {
            service.getLinks(10L);
        });

        //Act
        ListLinkResponse actualList = service.getLinks(1L);

        //Assert
        assertEquals(expectedLinksList, actualList);
    }

}

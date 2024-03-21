package edu.java.scrapper;

import edu.java.dao.JdbcChatDao;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.service.ScrapperService;
import edu.java.service.jdbc.JdbcChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ScrapperServiceTest {
    private ScrapperService service;

    /*@BeforeEach
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
        try {
            service.registerChat(2L);
        } catch (BadRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Check the chat for existence before deleting ")
    public void deleteUnexistedChatTest() {
        try {
            service.deleteChat(3L);
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Check for link re-adding")
    public void addLinkTwiceTest() throws Exception {
        try {
            service.addLink(1L, new LinkResponse(1L, new URI("link1")));
        } catch (BadRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Check the link for existence before deleting")
    public void deleteUnexistedLinkTest() throws Exception {
        try {
            service.deleteLink(1L, new LinkResponse(1L, new URI("link100500")));
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Getting links check")
    public void getLinksTest() throws Exception {
        ListLinkResponse expectedLinksList = new ListLinkResponse(Arrays.asList(
            new LinkResponse(1L, new URI("link1")),
            new LinkResponse(2L, new URI("link2")),
            new LinkResponse(3L, new URI("link3"))
        ), 0);

        try {
            service.getLinks(10L);
        } catch (NotFoundException e) {
            assertTrue(true);
        }

        try {
            ListLinkResponse actualList = service.getLinks(1L);

            assertEquals(expectedLinksList, actualList);
        } catch (NotFoundException e) {
            fail();
        }
    }*/

}

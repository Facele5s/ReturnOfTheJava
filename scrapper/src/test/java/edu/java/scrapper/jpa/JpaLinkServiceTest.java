package edu.java.scrapper.jpa;

import edu.java.client.Client;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JpaLinkServiceTest extends IntegrationTest {
    private static final Long CHAT_ID = 100500L;
    private static final URI URL = URI.create("link");

    @Autowired
    private JpaLinkRepository linkRepository;

    @Autowired
    private JpaChatRepository chatRepository;

    @Autowired
    private List<Client> availableClients;

    private LinkService linkService;
    private ChatService chatService;

    @BeforeEach
    public void setup() {
        linkService = new JpaLinkService(linkRepository, chatRepository, availableClients);
        chatService = new JpaChatService(chatRepository, linkRepository);
    }

    @Test
    @DisplayName("Link adding")
    @Transactional
    @Rollback
    public void addLinkTest() throws Exception {
        //Arrange
        chatService.registerChat(CHAT_ID);

        //Act
        linkService.add(CHAT_ID, URL);

        //Assert
        assertTrue(linkRepository.existsByUrl(URL));
    }

    @Test
    @DisplayName("Link removing")
    @Transactional
    @Rollback
    public void removeLinkTest() throws Exception {
        //Arrange
        chatService.registerChat(CHAT_ID);
        linkService.add(CHAT_ID, URL);
        Long linkId = linkRepository.findByUrl(URL).getId();

        //Act
        linkService.removeById(linkId);

        //Assert
        assertFalse(linkRepository.existsByUrl(URL));
    }

    @Test
    @DisplayName("All links getting")
    @Transactional
    @Rollback
    public void findAllLinksTest() throws Exception {
        //Arrange
        URI url1 = URI.create("link1");
        URI url2 = URI.create("link2");
        chatService.registerChat(CHAT_ID);
        linkService.add(CHAT_ID, url1);
        linkService.add(CHAT_ID, url2);

        //Act
        ListLinkResponse links = linkService.getAllLinks();

        //Assert
        assertEquals(2, links.size());
    }

    @Test
    @DisplayName("Getting link by id")
    @Transactional
    @Rollback
    public void findLinkByIdTest() throws Exception {
        //Arrange
        chatService.registerChat(CHAT_ID);
        linkService.add(CHAT_ID, URL);
        Long linkId = linkRepository.findByUrl(URL).getId();

        //Act + Assert
        assertDoesNotThrow(() -> {
            linkService.getLinkById(linkId);
        });
    }

    @Test
    @DisplayName("Getting links by chat")
    @Transactional
    @Rollback
    public void findLinksByChatTest() throws Exception {
        //Arrange
        URI url1 = URI.create("link1");
        URI url2 = URI.create("link2");
        chatService.registerChat(CHAT_ID);
        chatService.registerChat(100L);
        linkService.add(CHAT_ID, url1);
        linkService.add(CHAT_ID, url2);
        linkService.add(100L, url1);

        //Act
        Collection<URI> links = linkService.getLinksByChat(CHAT_ID).links().stream()
            .map(LinkResponse::url)
            .toList();

        //Assert
        assertEquals(2, links.size());
        assertTrue(links.contains(url1));
        assertTrue(links.contains(url2));
    }
}

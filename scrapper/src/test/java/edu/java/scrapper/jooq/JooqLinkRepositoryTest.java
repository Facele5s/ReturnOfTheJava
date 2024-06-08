package edu.java.scrapper.jooq;

import edu.java.entity.Chat;
import edu.java.entity.Link;
import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import org.junit.jupiter.api.Disabled;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqChatRepository chatRepository;

    @Autowired
    private JooqLinkRepository linkRepository;

    @Test
    @DisplayName("Link adding")
    @Transactional
    @Rollback
    public void addLinkTest() {
        //Arrange
        chatRepository.add(1L, null);

        //Act
        linkRepository.add(1L, URI.create("link1"));

        //Assert
        assertEquals(1, linkRepository.findAll().size());
        assertNotNull(linkRepository.findByUrl(URI.create("link1")));
    }

    @Test
    @DisplayName("Link removing")
    @Transactional
    @Rollback
    public void removeLinkTest() {
        //Arrange
        chatRepository.add(1L, null);
        linkRepository.add(1L, URI.create("link1"));
        Long linkId = linkRepository.findByUrl(URI.create("link1")).getId();

        //Act
        linkRepository.remove(linkId);

        //Assert
        assertEquals(0, linkRepository.findAll().size());
        assertNull(linkRepository.findById(linkId));
    }

    @Test
    @DisplayName("All links getting")
    @Transactional
    @Rollback
    public void findAllLinksTest() {
        //Arrange
        chatRepository.add(1L, null);
        linkRepository.add(1L, URI.create("link1"));
        linkRepository.add(1L, URI.create("link2"));
        linkRepository.add(1L, URI.create("link3"));

        //Act
        Collection<Link> links = linkRepository.findAll();

        //Assert
        assertEquals(3, links.size());
    }

    @Test
    @DisplayName("Getting link by id")
    @Transactional
    @Rollback
    public void findLinkByIdTest() {
        //Arrange
        chatRepository.add(1L, null);
        linkRepository.add(1L, URI.create("link1"));
        Long linkId = linkRepository.findByUrl(URI.create("link1")).getId();

        //Act + Assert
        assertDoesNotThrow(() -> {
            linkRepository.findById(linkId);
        });
    }

    @Test
    @Disabled
    @DisplayName("Getting links by chat")
    @Transactional
    @Rollback
    public void findLinksByChatTest() {
        //Arrange
        chatRepository.add(1L, null);
        chatRepository.add(2L, null);
        linkRepository.add(1L, URI.create("link1"));
        linkRepository.add(1L, URI.create("link2"));
        linkRepository.add(2L, URI.create("link3"));

        //Act
        Collection<Link> links = linkRepository.findByChat(1L);

        //Assert
        assertEquals(2, links.size());
        assertEquals(3, linkRepository.findAll().size());
    }

    @Test
    @Disabled
    @DisplayName("Getting chats with link")
    @Transactional
    @Rollback
    public void findChatsByLinkTest() {
        //Arrange
        chatRepository.add(1L, null);
        chatRepository.add(2L, null);
        chatRepository.add(3L, null);
        Long linkId = linkRepository.add(1L, URI.create("link1")).getId();
        linkRepository.add(1L, URI.create("link2"));
        linkRepository.add(2L, URI.create("link1"));

        //Act
        List<Long> chatsWithUrl = chatRepository.findByLink(linkId)
            .stream()
            .map(Chat::getId)
            .toList();

        //Assert
        assertEquals(2, chatsWithUrl.size());
        assertTrue(chatsWithUrl.contains(1L));
        assertTrue(chatsWithUrl.contains(2L));
        assertFalse(chatsWithUrl.contains(3L));
    }
}

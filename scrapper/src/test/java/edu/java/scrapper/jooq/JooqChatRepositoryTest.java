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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class JooqChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqChatRepository chatRepository;

    @Autowired
    private JooqLinkRepository linkRepository;

    @Test
    @DisplayName("Chat adding")
    @Transactional
    @Rollback
    public void addChatTest() {
        //Act
        chatRepository.add(1L, null);

        //Assert
        assertEquals(1, chatRepository.findAll().size());
        assertNotNull(chatRepository.findById(1L));
    }

    @Test
    @DisplayName("Chat removing")
    @Transactional
    @Rollback
    public void removeChatTest() {
        //Arrange
        chatRepository.add(1L, null);

        //Act
        chatRepository.remove(1L);

        //Assert
        assertEquals(0, chatRepository.findAll().size());
        assertNull(chatRepository.findById(1L));
    }

    @Test
    @DisplayName("All chats getting")
    @Transactional
    @Rollback
    public void findAllChatsTest() {
        //Arrange
        chatRepository.add(1L, null);
        chatRepository.add(2L, null);

        //Act
        Collection<Chat> chats = chatRepository.findAll();

        //Assert
        assertEquals(2, chats.size());
    }

    @Test
    @DisplayName("Getting chat by id")
    @Transactional
    @Rollback
    public void findChatByIdTest() {
        //Arrange
        chatRepository.add(1L, null);

        //Act + Assert
        assertDoesNotThrow(() -> {
            chatRepository.findById(1L);
        });
    }

    @Test
    @Disabled
    @DisplayName("Getting chats by link")
    @Transactional
    @Rollback
    public void findChatByLinkTest() {
        //Arrange
        chatRepository.add(1L, null);
        linkRepository.add(1L, URI.create("link1"));
        Link link = linkRepository.findByUrl(URI.create("link1"));

        //Act
        Collection<Chat> chats = chatRepository.findByLink(link.getId());
        System.out.println(chats);
        System.out.println(linkRepository.findAll());
        System.out.println(chatRepository.findAll());

        //Assert
        assertEquals(1, chats.size());
    }
}

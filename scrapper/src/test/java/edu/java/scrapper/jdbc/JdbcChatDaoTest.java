package edu.java.scrapper.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.entity.jdbc.Chat;
import edu.java.entity.jdbc.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;

    @Autowired
    private JdbcChatDao chatDao;

    @Test
    @DisplayName("Chat adding")
    @Transactional
    @Rollback
    public void addChatTest() {
        //Act
        chatDao.add(1L, null);

        //Assert
        assertEquals(1, chatDao.findAll().size());
        assertNotNull(chatDao.findById(1L));
    }

    @Test
    @DisplayName("Chat removing")
    @Transactional
    @Rollback
    public void removeChatTest() {
        //Arrange
        chatDao.add(1L, null);

        //Act
        chatDao.remove(1L);

        //Assert
        assertEquals(0, chatDao.findAll().size());
        assertThrows(EmptyResultDataAccessException.class, () -> {
           chatDao.findById(1L);
        });
    }

    @Test
    @DisplayName("All chats getting")
    @Transactional
    @Rollback
    public void findAllChatsTest() {
        //Arrange
        chatDao.add(1L, null);
        chatDao.add(2L, null);

        //Act
        Collection<Chat> chats = chatDao.findAll();

        //Assert
        assertEquals(2, chats.size());
    }

    @Test
    @DisplayName("Getting chat by id")
    @Transactional
    @Rollback
    public void findChatByIdTest() {
        //Arrange
        chatDao.add(1L, null);

        //Act + Assert
        assertDoesNotThrow(() -> {
            chatDao.findById(1L);
        });
    }

    @Test
    @DisplayName("Getting chats by link")
    @Transactional
    @Rollback
    public void findChatByLinkTest() {
        //Arrange
        chatDao.add(1L, null);
        linkDao.add(1L, URI.create("link1"));
        Link link = linkDao.findByUrl(URI.create("link1"));

        //Act
        Collection<Chat> chats = chatDao.findByLink(link.getId());

        //Assert
        assertEquals(1, chats.size());
    }
}

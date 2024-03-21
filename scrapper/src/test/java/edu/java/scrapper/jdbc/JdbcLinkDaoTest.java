package edu.java.scrapper.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.entity.Link;
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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcLinkDaoTest extends IntegrationTest {
    @Autowired
    private JdbcChatDao chatDao;

    @Autowired
    private JdbcLinkDao linkDao;

    @Test
    @DisplayName("Link adding")
    @Transactional
    @Rollback
    public void addLinkTest() {
        //Arrange
        chatDao.add(1L, null);

        //Act
        linkDao.add(1L, URI.create("link1"));

        //Assert
        assertEquals(1, linkDao.findAll().size());
        assertNotNull(linkDao.findByUrl(URI.create("link1")));
    }

    @Test
    @DisplayName("Link removing")
    @Transactional
    @Rollback
    public void removeLinkTest() {
        //Arrange
        chatDao.add(1L, null);
        linkDao.add(1L, URI.create("link1"));
        Long linkId = linkDao.findByUrl(URI.create("link1"))
            .stream().map(Link::getId).findFirst().orElse(0L);

        //Act
        linkDao.remove(linkId);

        //Assert
        assertEquals(0, linkDao.findAll().size());
        assertThrows(EmptyResultDataAccessException.class, () -> {
            linkDao.findById(linkId);
        });
    }

    @Test
    @DisplayName("All links getting")
    @Transactional
    @Rollback
    public void findAllLinksTest() {
        //Arrange
        chatDao.add(1L, null);
        linkDao.add(1L, URI.create("link1"));
        linkDao.add(1L, URI.create("link2"));
        linkDao.add(1L, URI.create("link3"));

        //Act
        Collection<Link> links = linkDao.findAll();

        //Assert
        assertEquals(3, links.size());
    }

    @Test
    @DisplayName("Getting link by id")
    @Transactional
    @Rollback
    public void findLinkByIdTest() {
        //Arrange
        chatDao.add(1L, null);
        linkDao.add(1L, URI.create("link1"));

        //Act + Assert
        assertDoesNotThrow(() -> {
            linkDao.findById(1L);
        });
    }

    @Test
    @DisplayName("Getting links by chat")
    @Transactional
    @Rollback
    public void findLinksByChatTest() {
        //Arrange
        chatDao.add(1L, null);
        chatDao.add(2L, null);
        linkDao.add(1L, URI.create("link1"));
        linkDao.add(1L, URI.create("link2"));
        linkDao.add(2L, URI.create("link3"));

        //Act
        Collection<Link> links = linkDao.findByChat(1L);

        //Assert
        assertEquals(2, links.size());
        assertEquals(3, linkDao.findAll().size());
    }

    @Test
    @DisplayName("Getting links by URL")
    @Transactional
    @Rollback
    public void findLinksByUrlTest() {
        //Arrange
        chatDao.add(1L, null);
        chatDao.add(2L, null);
        chatDao.add(3L, null);
        linkDao.add(1L, URI.create("link1"));
        linkDao.add(1L, URI.create("link2"));
        linkDao.add(2L, URI.create("link1"));

        //Act
        List<Long> chatsWithUrl = linkDao.findByUrl(URI.create("link1"))
            .stream()
            .map(Link::getChatId)
            .toList();

        //Assert
        assertEquals(2, chatsWithUrl.size());
        assertTrue(chatsWithUrl.contains(1L));
        assertTrue(chatsWithUrl.contains(2L));
        assertFalse(chatsWithUrl.contains(3L));
    }
}

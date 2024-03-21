package edu.java.scrapper.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.dto.entity.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;

    @Autowired
    private JdbcChatDao chatDao;


    /*@Test
    @Transactional
    //@Rollback
    public void testAddLink() {
        chatDao.add(null);

        linkDao.add(1L, URI.create("linkk"));

        Collection<Link> list = linkDao.findAll();

        assertEquals(list.size(), 1);

        List<Link> links = (List<Link>) linkDao.findByChat(1L);
        assertEquals(links.getFirst().getUrl(), URI.create("linkk"));


    }*/
}

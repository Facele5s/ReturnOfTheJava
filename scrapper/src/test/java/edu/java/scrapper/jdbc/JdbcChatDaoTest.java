package edu.java.scrapper.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.entity.Chat;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;

    @Autowired
    private JdbcChatDao chatDao;


    @Test
    @Transactional
    @Rollback
    public void testAddChat() {
        chatDao.add(1L, null);

        Chat chat = chatDao.findById(1L);

        System.out.println(chat.getRegistrationDate());
    }
}

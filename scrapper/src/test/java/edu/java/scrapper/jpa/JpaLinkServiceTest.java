package edu.java.scrapper.jpa;

import edu.java.repository.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
public class JpaLinkServiceTest extends IntegrationTest {
    /*private static final Long CHAT_ID = 100500L;
    private static final URI URL = URI.create("link");

    @Autowired
    private JpaLinkRepository linkRepository;

    private LinkService linkService;

    @BeforeEach
    public void setup() {
        linkService = new JpaLinkService(linkRepository);
    }

    @Test
    @DisplayName("Link adding")
    @Transactional
    @Rollback
    public void addLinkTest() throws Exception {
        //Arrange
        LinkEntity entity = new LinkEntity();

        //Act
        linkService.add(CHAT_ID, URL);

        //Assert
        //assertTrue(linkRepository.exists(entity));
    }*/


}

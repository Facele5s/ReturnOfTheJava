package edu.java.scrapper.jpa;

import edu.java.dto.response.ListChatResponse;
import edu.java.repository.JpaChatRepository;
import edu.java.repository.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.ChatService;
import edu.java.service.jpa.JpaChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JpaChatServiceTest extends IntegrationTest {
    private static final Long CHAT_ID = 100500L;

    @Autowired
    private JpaChatRepository chatRepository;

    @Autowired
    private JpaLinkRepository linkRepository;

    private ChatService chatService;

    @BeforeEach
    public void setup() {
        chatService = new JpaChatService(chatRepository, linkRepository);
    }

    @Test
    @DisplayName("Chat registration")
    @Transactional
    @Rollback
    public void registerChatTest() throws Exception {
        //Act
        chatService.registerChat(CHAT_ID);

        //Assert
        assertTrue(chatRepository.existsById(CHAT_ID));
    }

    @Test
    @DisplayName("Chat deleting")
    @Transactional
    @Rollback
    public void deleteChatTest() throws Exception {
        //Arrange
        chatService.registerChat(CHAT_ID);

        //Act
        chatService.deleteChat(CHAT_ID);

        //Assert
        assertFalse(chatRepository.existsById(CHAT_ID));
    }

    @Test
    @DisplayName("All chats getting")
    @Transactional
    @Rollback
    public void getAllChatsTest() throws Exception {
        //Arrange
        chatService.registerChat(CHAT_ID);

        //Act
        ListChatResponse listChatResponse = chatService.getAllChats();

        //Assert
        assertEquals(1, listChatResponse.chats().size());
        assertEquals(CHAT_ID, listChatResponse.chats().getFirst().id());
    }
}

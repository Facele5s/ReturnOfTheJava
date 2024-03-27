package edu.java.service.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.ListChatResponse;
import edu.java.entity.Chat;
import edu.java.service.ChatService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private static final String MSG_CHAT_ALREADY_REGISTERED = "The chat is already registered";
    private static final String MSG_CHAT_NOT_REGISTERED = "The chat is not registered yet";

    private static final String DESC_REGISTER_TWICE = "You can't register the chat twice";
    private static final String DESC_DEL_UNREG = "You can't delete an unregistered chat";
    private static final String DESC_GET_UNREG = "You can't get an unregistered chat";

    private final JdbcChatDao chatDao;

    @Override
    public ChatResponse registerChat(Long chatId) throws BadRequestException {
        try {
            Chat chat = chatDao.add(chatId, OffsetDateTime.now());

            return new ChatResponse(chat.getId(), chat.getRegistrationDate());
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_CHAT_ALREADY_REGISTERED,
                DESC_REGISTER_TWICE
            );
        }
    }

    @Override
    public ChatResponse deleteChat(Long chatId) throws NotFoundException {
        try {
            Chat chat = chatDao.remove(chatId);

            return new ChatResponse(chat.getId(), chat.getRegistrationDate());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_DEL_UNREG
            );
        }
    }

    @Override
    public ListChatResponse getAllChats() {
        List<ChatResponse> chatResponseList = chatDao.findAll()
            .stream()
            .map(chat -> new ChatResponse(chat.getId(), chat.getRegistrationDate()))
            .toList();

        return new ListChatResponse(chatResponseList, chatResponseList.size());
    }

    @Override
    public ChatResponse getChatById(Long chatId) throws NotFoundException {
        try {
            Chat chat = chatDao.findById(chatId);

            return new ChatResponse(chat.getId(), chat.getRegistrationDate());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_GET_UNREG
            );
        }
    }

    @Override
    public Collection<Chat> getChatByLink(Long linkId) {
        return chatDao.findByLink(linkId);
    }
}

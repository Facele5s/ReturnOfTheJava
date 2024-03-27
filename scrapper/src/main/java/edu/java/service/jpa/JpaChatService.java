package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.ListChatResponse;
import edu.java.entity.Chat;
import edu.java.entity.ChatEntity;
import edu.java.repository.JpaChatRepository;
import edu.java.service.ChatService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository chatRepository;

    @Override
    public ChatResponse registerChat(Long chatId) throws BadRequestException {
        ChatEntity entity = new ChatEntity();
        entity.setId(chatId);
        entity.setRegistrationDate(OffsetDateTime.now());

        chatRepository.save(entity);

        ChatEntity savedChat = chatRepository.findById(chatId).get();
        return new ChatResponse(savedChat.getId(), savedChat.getRegistrationDate());
    }

    @Override
    public ChatResponse deleteChat(Long chatId) throws NotFoundException {
        ChatEntity entity = chatRepository.findById(chatId).get();

        chatRepository.delete(entity);

        return new ChatResponse(entity.getId(), entity.getRegistrationDate());
    }

    @Override
    public ListChatResponse getAllChats() {
        List<ChatResponse> chatResponseList = chatRepository.findAll().stream()
            .map(c -> new ChatResponse(c.getId(), c.getRegistrationDate()))
            .toList();

        return new ListChatResponse(chatResponseList, chatResponseList.size());
    }

    @Override
    public ChatResponse getChatById(Long chatId) throws NotFoundException {
        ChatEntity entity = chatRepository.findById(chatId).get();

        return new ChatResponse(entity.getId(), entity.getRegistrationDate());
    }

    @Override
    public Collection<Chat> getChatByLink(Long linkId) {
        return chatRepository.findByLinkId(linkId).stream()
            .map(c -> new Chat(c.getId(), c.getRegistrationDate()))
            .toList();
    }
}

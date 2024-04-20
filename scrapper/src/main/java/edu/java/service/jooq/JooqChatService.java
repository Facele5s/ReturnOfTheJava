package edu.java.service.jooq;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.ListChatResponse;
import edu.java.entity.Chat;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.service.ChatService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository chatRepository;

    @Override
    public ChatResponse registerChat(Long chatId) throws BadRequestException {
        Chat chat = chatRepository.add(chatId, OffsetDateTime.now());

        return new ChatResponse(chat.getId(), chat.getRegistrationDate());
    }

    @Override
    public ChatResponse deleteChat(Long chatId) {
        Chat chat = chatRepository.remove(chatId);

        return new ChatResponse(chat.getId(), chat.getRegistrationDate());
    }

    @Override
    public ListChatResponse getAllChats() {
        List<ChatResponse> chatResponseList = chatRepository.findAll()
            .stream()
            .map(chat -> new ChatResponse(chat.getId(), chat.getRegistrationDate()))
            .toList();

        return new ListChatResponse(chatResponseList, chatResponseList.size());
    }

    @Override
    public ChatResponse getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId);

        return new ChatResponse(chat.getId(), chat.getRegistrationDate());
    }

    @Override
    public Collection<Chat> getChatsByLink(Long linkId) {
        return chatRepository.findByLink(linkId);
    }
}

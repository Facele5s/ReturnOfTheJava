package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.ListChatResponse;
import edu.java.entity.Chat;
import edu.java.entity.Link;
import edu.java.repository.JpaChatRepository;
import edu.java.repository.JpaLinkRepository;
import edu.java.service.ChatService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;

    @Override
    public ChatResponse registerChat(Long chatId) throws BadRequestException {
        Chat chat = new Chat();
        chat.setId(chatId);
        chat.setRegistrationDate(OffsetDateTime.now());

        chatRepository.save(chat);

        Chat savedChat = chatRepository.findById(chatId).get();
        return new ChatResponse(savedChat.getId(), savedChat.getRegistrationDate());
    }

    @Override
    public ChatResponse deleteChat(Long chatId) throws NotFoundException {
        Chat chat = chatRepository.findById(chatId).get();

        chatRepository.deleteById(chatId);

        return new ChatResponse(chat.getId(), chat.getRegistrationDate());
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
        Chat chat = chatRepository.findById(chatId).get();

        return new ChatResponse(chat.getId(), chat.getRegistrationDate());
    }

    @Override
    public Collection<Chat> getChatsByLink(Long linkId) {
        Link link = linkRepository.findById(linkId).get();

        return chatRepository.findChatEntitiesByLinksContains(link);
    }
}

package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperService {
    private final ChatService chatService;
    private final LinkService linkService;

    public void registerChat(Long chatId) throws BadRequestException {
        chatService.registerChat(chatId);
    }

    public void deleteChat(Long chatId) throws NotFoundException {
        chatService.deleteChat(chatId);
    }

    public ListLinkResponse getLinks(Long chatId) throws NotFoundException {
        return linkService.getLinksByChat(chatId);
    }

    public LinkResponse addLink(LinkResponse link) throws BadRequestException, NotFoundException {
        return linkService.add(link.chatId(), link.url());
    }

    public LinkResponse deleteLink(LinkResponse link) throws NotFoundException {
        return linkService.untrack(link.chatId(), link.url());
    }
}

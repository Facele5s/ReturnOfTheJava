package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperService {
    private final ChatService chatService;
    private final LinkService linkService;

    private static final String MSG_CHAT_NOT_REGISTERED = "The chat is not registered yet";
    private static final String MSG_CHAT_ALREADY_REGISTERED = "The chat is already registered";
    private static final String MSG_LINK_ALREADY_ADDED = "The link is already added";
    private static final String MSG_LINK_NOT_TRACKED = "The link is not tracked";

    private static final String DESC_REGISTER_TWICE = "You can't register the chat twice";
    private static final String DESC_DEL_UNREG = "You can't delete an unregistered chat";
    private static final String DESC_GET_LINKS_UNREG = "You can't get links for an unregistered chat";
    private static final String DESC_ADD_LINK_UNREG = "You can't add link for an unregistered chat";
    private static final String DESC_ADD_LINK_ADDED = "You can't add an already added link";
    private static final String DESC_DEL_LINK_UNREG = "You can't delete link for an unregistered chat";
    private static final String DESC_DEL_LINK_UNTRACKED = "You can't delete an untracked link";

    public void registerChat(Long chatId) throws BadRequestException {
        List<Long> chatIds = getChatIds();

        if (chatIds.contains(chatId)) {
            throw new BadRequestException(
                MSG_CHAT_ALREADY_REGISTERED,
                DESC_REGISTER_TWICE
            );
        }

        chatService.registerChat(chatId);
    }

    public void deleteChat(Long chatId) throws NotFoundException {
        List<Long> chatIds = getChatIds();

        if (!chatIds.contains(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_DEL_UNREG
            );
        }

        chatService.deleteChat(chatId);
    }

    public ListLinkResponse getLinks(Long chatId) throws NotFoundException {
        List<Long> chatIds = getChatIds();

        if (!chatIds.contains(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_GET_LINKS_UNREG
            );
        }

        return linkService.getLinksByChat(chatId);
    }

    public LinkResponse addLink(LinkResponse link) throws BadRequestException, NotFoundException {
        List<Long> chatIds = getChatIds();

        if (!chatIds.contains(link.chatId())) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_ADD_LINK_UNREG
            );
        }

        ListLinkResponse linkResponseList = linkService.getLinksByChat(link.chatId());

        if (linkResponseList.links().contains(link)) {
            throw new BadRequestException(
                MSG_LINK_ALREADY_ADDED,
                DESC_ADD_LINK_ADDED
            );
        }

        return linkService.add(link.chatId(), link.url());
    }

    public LinkResponse deleteLink(LinkResponse link) throws BadRequestException, NotFoundException {
        List<Long> chatIds = getChatIds();

        if (!chatIds.contains(link.chatId())) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_DEL_LINK_UNREG
            );
        }

        List<URI> linkUrls = linkService.getLinksByChat(link.chatId())
            .links().stream()
            .map(LinkResponse::url)
            .toList();

        Long linkId = linkService.getLinksByChat(link.chatId())
            .links().stream()
            .filter(l -> l.url().equals(link.url()))
            .map(LinkResponse::chatId)
            .findFirst().orElse(-1L);

        if (!linkUrls.contains(link.url())) {
            throw new BadRequestException(
                MSG_LINK_NOT_TRACKED,
                DESC_DEL_LINK_UNTRACKED
            );
        }

        return linkService.removeById(linkId);
    }

    private List<Long> getChatIds() {
        return chatService.getAllChats().chats().stream()
            .map(ChatResponse::id).toList();
    }
}

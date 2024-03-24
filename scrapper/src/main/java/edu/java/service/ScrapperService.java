package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {
    private final Map<Long, ListLinkResponse> linksByChats = new HashMap<>();

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
        if (linksByChats.containsKey(chatId)) {
            throw new BadRequestException(
                MSG_CHAT_ALREADY_REGISTERED,
                DESC_REGISTER_TWICE
            );
        }

        linksByChats.put(chatId, new ListLinkResponse(new ArrayList<>(), 0));
    }

    public void deleteChat(Long chatId) throws NotFoundException {
        if (!linksByChats.containsKey(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_DEL_UNREG
            );
        }

        linksByChats.remove(chatId);
    }

    public ListLinkResponse getLinks(Long chatId) throws NotFoundException {
        if (!linksByChats.containsKey(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_GET_LINKS_UNREG
            );
        }

        return linksByChats.get(chatId);
    }

    public LinkResponse addLink(Long chatId, LinkResponse link) throws BadRequestException, NotFoundException {
        if (!linksByChats.containsKey(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_ADD_LINK_UNREG
            );
        }

        ListLinkResponse linkResponseList = linksByChats.get(chatId);

        if (linkResponseList.links().contains(link)) {
            throw new BadRequestException(
                MSG_LINK_ALREADY_ADDED,
                DESC_ADD_LINK_ADDED
            );
        }

        linkResponseList.links().add(link);

        return link;
    }

    public LinkResponse deleteLink(Long chatId, LinkResponse link) throws BadRequestException, NotFoundException {
        if (!linksByChats.containsKey(chatId)) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_DEL_LINK_UNREG
            );
        }

        ListLinkResponse linkResponseList = linksByChats.get(chatId);

        if (!linkResponseList.links().contains(link)) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_DEL_LINK_UNTRACKED
            );
        }

        linkResponseList.links().remove(link);

        return link;
    }
}

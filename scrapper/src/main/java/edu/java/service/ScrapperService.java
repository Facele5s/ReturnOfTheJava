package edu.java.service;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {
    private final Map<Long, ListLinkResponse> chats = new HashMap<>();

    public void registerChat(Long chatId) {
        chats.put(chatId, new ListLinkResponse(new ArrayList<>(), 0));
    }

    public void deleteChat(Long chatId) {
        chats.remove(chatId);
    }

    public ListLinkResponse getLinks(Long chatId) {
        return chats.get(chatId);
    }

    public LinkResponse addLink(Long chatId, LinkResponse link) {
        ListLinkResponse linkResponseList = chats.get(chatId);
        linkResponseList.links().add(link);

        return link;
    }

    public LinkResponse deleteLink(Long chatId, LinkResponse link) {
        ListLinkResponse linkResponseList = chats.get(chatId);
        linkResponseList.links().remove(link);

        return link;
    }
}

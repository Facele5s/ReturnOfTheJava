package edu.java.service;

import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinkResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {
    private Map<Long, ListLinkResponse> chats = new HashMap<>();

    public void registerChat(Long id) {
        chats.put(id, new ListLinkResponse(new ArrayList<>(), 0));
    }

    public void deleteChat(Long id) {
        chats.remove(id);
    }

    public ListLinkResponse getLinks(Long id) {
        return chats.get(id);
    }

    public LinkResponse addLink(Long id, LinkResponse link) {
        ListLinkResponse links = chats.get(id);
        links.links().add(link);

        return link;
    }

    public LinkResponse deleteLink(Long id, LinkResponse link) {
        ListLinkResponse links = chats.get(id);
        links.links().remove(link);

        return link;
    }
}

package edu.java.service.jpa;

import edu.java.client.Client;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Chat;
import edu.java.entity.Link;
import edu.java.repository.JpaChatRepository;
import edu.java.repository.JpaLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final List<Client> availableClients;

    @Override
    public LinkResponse add(Long chatId, URI url) {
        Client client = availableClients
            .stream()
            .filter(c -> c.isLinkSupported(url))
            .findFirst().orElse(null);

        Chat chat = chatRepository.findById(chatId).get();

        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            link = new Link();
            link.setUrl(url);
            link.setUpdatedAt(OffsetDateTime.now());
            link.setCheckedAt(OffsetDateTime.now());
        }
        chat.getLinks().add(link);

        linkRepository.save(link);
        chatRepository.save(chat);
        client.addLinkData(url);

        Link savedLink = linkRepository.findByUrl(url);
        return new LinkResponse(chatId, savedLink.getUrl());
    }

    @Override
    public LinkResponse removeById(Long linkId) {
        Link link = linkRepository.findById(linkId).get();

        chatRepository.findChatEntitiesByLinksContains(link)
            .forEach(chat -> {
                chat.getLinks().remove(link);
            });
        linkRepository.delete(link);

        return new LinkResponse(null, link.getUrl());
    }

    @Override
    public LinkResponse untrack(Long chatId, URI url) {
        Chat chat = chatRepository.findById(chatId).get();
        Link link = linkRepository.findByUrl(url);

        chat.getLinks().remove(link);
        link.getChats().remove(chat);
        linkRepository.save(link);
        chatRepository.save(chat);
        if (link.getChats().isEmpty()) {
            linkRepository.delete(link);
        }

        return new LinkResponse(chatId, link.getUrl());
    }

    @Override
    public ListLinkResponse getAllLinks() {
        List<LinkResponse> linkResponseList = linkRepository.findAll().stream()
            .map(l -> new LinkResponse(null, l.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse getLinkById(Long linkId) {
        Link entity = linkRepository.findById(linkId).get();

        return new LinkResponse(null, entity.getUrl());
    }

    @Override
    public ListLinkResponse getLinksByChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).get();

        List<LinkResponse> linkResponseList = linkRepository.findLinksByChatsContains(chat).stream()
            .map(l -> new LinkResponse(chatId, l.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public Collection<Link> getLongUncheckedLinks(Duration duration) {
        OffsetDateTime dateTime = OffsetDateTime.now()
            .minusSeconds(duration.getSeconds());

        return linkRepository.findLinksByCheckedAtBefore(dateTime);
    }

    @Override
    public LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) {
        Link entity = linkRepository.findById(linkId).get();

        entity.setUpdatedAt(updateDate);
        linkRepository.save(entity);

        return new LinkResponse(null, entity.getUrl());
    }

    @Override
    public LinkResponse setLastCheckDate(Long linkId, OffsetDateTime checkDate) {
        Link entity = linkRepository.findById(linkId).get();

        entity.setCheckedAt(checkDate);
        linkRepository.save(entity);

        return new LinkResponse(null, entity.getUrl());
    }
}

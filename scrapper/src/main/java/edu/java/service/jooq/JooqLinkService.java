package edu.java.service.jooq;

import edu.java.client.Client;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Link;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final List<Client> availableClients;

    @Override
    public LinkResponse add(Long chatId, URI url) throws BadRequestException, NotFoundException {
        Client client = availableClients
            .stream()
            .filter(c -> c.isLinkSupported(url))
            .findFirst().orElse(null);

        Link link = linkRepository.add(chatId, url);
        if (client != null) {
            client.addLinkData(url, link.getId());
        }

        return new LinkResponse(chatId, link.getUrl());
    }

    @Override
    public LinkResponse removeById(Long linkId) {
        Link link = linkRepository.remove(linkId);

        return new LinkResponse(null, link.getUrl());
    }

    @Override
    public LinkResponse untrack(Long chatId, URI url) {
        Link link = linkRepository.untrack(chatId, url);

        return new LinkResponse(chatId, link.getUrl());
    }

    @Override
    public ListLinkResponse getAllLinks() {
        List<LinkResponse> linkResponseList = linkRepository.findAll()
            .stream()
            .map(link -> new LinkResponse(null, link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse getLinkById(Long linkId) {
        Link link = linkRepository.findById(linkId);

        return new LinkResponse(null, link.getUrl());
    }

    @Override
    public ListLinkResponse getLinksByChat(Long chatId) {
        List<LinkResponse> linkResponseList = linkRepository.findByChat(chatId)
            .stream()
            .map(link -> new LinkResponse(chatId, link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public Collection<Link> getLongUncheckedLinks(Duration duration) {
        return linkRepository.findLongUnchecked(duration);
    }

    @Override
    public LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) {
        Link link = linkRepository.updateLink(linkId, updateDate);

        return new LinkResponse(null, link.getUrl());
    }

    @Override
    public LinkResponse setLastCheckDate(Long linkId, OffsetDateTime checkDate) {
        Link link = linkRepository.checkLink(linkId, checkDate);

        return new LinkResponse(null, link.getUrl());
    }
}

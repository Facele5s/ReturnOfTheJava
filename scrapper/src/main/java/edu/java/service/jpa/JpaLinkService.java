package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Link;
import edu.java.entity.LinkEntity;
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

    @Override
    public LinkResponse add(Long chatId, URI url) throws BadRequestException {
        LinkEntity entity = new LinkEntity();
        entity.setChatId(chatId);
        entity.setUrl(url);

        linkRepository.save(entity);

        LinkEntity savedLink = linkRepository.findByUrl(chatId, url);
        return new LinkResponse(savedLink.getChatId(), savedLink.getUrl());
    }

    @Override
    public LinkResponse removeById(Long linkId) throws NotFoundException {
        LinkEntity entity = linkRepository.findById(linkId).get();

        linkRepository.delete(entity);

        return new LinkResponse(entity.getChatId(), entity.getUrl());
    }

    @Override
    public LinkResponse removeByUrl(Long chatId, URI url) throws NotFoundException {
        LinkEntity entity = linkRepository.findByUrl(chatId, url);

        linkRepository.delete(entity);

        return new LinkResponse(entity.getChatId(), entity.getUrl());
    }

    @Override
    public ListLinkResponse getAllLinks() {
        List<LinkResponse> linkResponseList = linkRepository.findAll().stream()
            .map(l -> new LinkResponse(l.getChatId(), l.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse getLinkById(Long linkId) throws NotFoundException {
        LinkEntity entity = linkRepository.findById(linkId).get();

        return new LinkResponse(entity.getChatId(), entity.getUrl());
    }

    @Override
    public ListLinkResponse getLinksByChat(Long chatId) {
        List<LinkResponse> linkResponseList = linkRepository.findByChat(chatId).stream()
            .map(l -> new LinkResponse(l.getChatId(), l.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public ListLinkResponse getLinksByUrl(URI url) {
        List<LinkResponse> linkResponseList = linkRepository.findByUrl(url).stream()
            .map(l -> new LinkResponse(l.getChatId(), l.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public Collection<Link> getLongUncheckedLinks(Duration duration) {
        return linkRepository.findLongUnchecked(duration).stream()
            .map(l -> new Link(l.getId(), l.getChatId(), l.getUrl(), l.getUpdatedAt(), l.getCheckedAt()))
            .toList();
    }

    @Override
    public LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) throws NotFoundException {
        LinkEntity entity = linkRepository.findById(linkId).get();

        entity.setUpdatedAt(updateDate);
        linkRepository.save(entity);

        return new LinkResponse(entity.getChatId(), entity.getUrl());
    }

    @Override
    public LinkResponse setLastCheckDate(Long linkId, OffsetDateTime checkDate) throws NotFoundException {
        LinkEntity entity = linkRepository.findById(linkId).get();

        entity.setCheckedAt(checkDate);
        linkRepository.save(entity);

        return new LinkResponse(entity.getChatId(), entity.getUrl());
    }
}

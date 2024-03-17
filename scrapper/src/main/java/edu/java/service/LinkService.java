package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Link;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface LinkService {

    LinkResponse add(Long chatId, URI url) throws BadRequestException;

    LinkResponse remove(Long linkId) throws NotFoundException;

    ListLinkResponse getAllLinks();

    LinkResponse getLinkById(Long linkId) throws NotFoundException;

    ListLinkResponse getLinksByChat(Long chatId);

    ListLinkResponse getLinksByUrl(URI url);

    Collection<Link> getLongUncheckedLinks(Duration duration);

    LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) throws NotFoundException;

    LinkResponse setLastCheckDate(Long linkId, OffsetDateTime checkDate) throws NotFoundException;
}

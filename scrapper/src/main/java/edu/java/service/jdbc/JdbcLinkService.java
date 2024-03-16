package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private static final String MSG_LINK_ALREADY_ADDED = "The link is already added";
    private static final String MSG_LINK_NOT_TRACKED = "The link is not tracked";

    private static final String DESC_ADD_LINK_ADDED = "You can't add an already added link";
    private static final String DESC_DEL_LINK_UNTRACKED = "You can't delete an untracked link";
    private static final String DESC_GET_LINK_UNTRACKED = "You can't get an untracked link";
    private static final String DESC_UPDATE_LINK_UNTRACKED = "You can't update an untracked link";
    private static final String DESC_CHECK_LINK_UNTRACKED = "You can't check an untracked link";

    private final JdbcLinkDao linkDao;

    @Override
    public LinkResponse add(Long chatId, URI url) throws BadRequestException {
        try {
            Link link = linkDao.add(chatId, url);

            return new LinkResponse(link.getId(), link.getUrl());
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_LINK_ALREADY_ADDED,
                DESC_ADD_LINK_ADDED
            );
        }
    }

    @Override
    public LinkResponse remove(Long linkId) throws NotFoundException {
        try {
            Link link = linkDao.remove(linkId);

            return new LinkResponse(link.getId(), link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_DEL_LINK_UNTRACKED
            );
        }
    }

    @Override
    public ListLinkResponse getAllLinks() {
        List<LinkResponse> linkResponseList = linkDao.findAll()
            .stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse getLinkById(Long linkId) throws NotFoundException {
        try {
            Link link = linkDao.findById(linkId);

            return new LinkResponse(link.getId(), link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_GET_LINK_UNTRACKED
            );
        }
    }

    @Override
    public ListLinkResponse getLinksByChat(Long chatId) {
        List<LinkResponse> linkResponseList = linkDao.findByChat(chatId)
            .stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public ListLinkResponse getLinksByUrl(URI url) {
        List<LinkResponse> linkResponseList = linkDao.findByUrl(url)
            .stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public ListLinkResponse getLongUncheckedLinks(OffsetDateTime dateTime) {
        List<LinkResponse> linkResponseList = linkDao.findLongUnchecked(dateTime)
            .stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) throws NotFoundException {
        try {
            Link link = linkDao.updateLink(linkId, updateDate);

            return new LinkResponse(link.getId(), link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_UPDATE_LINK_UNTRACKED
            );
        }
    }

    @Override
    public LinkResponse setLastCheckDate(Long linkId, OffsetDateTime checkDate) throws NotFoundException {
        try {
            Link link = linkDao.checkLink(linkId, checkDate);

            return new LinkResponse(link.getId(), link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_CHECK_LINK_UNTRACKED
            );
        }
    }
}

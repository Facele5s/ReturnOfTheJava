package edu.java.service.jdbc;

import edu.java.client.Client;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.entity.Link;
import edu.java.scrapper.domain.jdbc.JdbcLinkDao;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private static final String MSG_LINK_ALREADY_ADDED = "The link is already added";
    private static final String MSG_LINK_NOT_TRACKED = "The link is not tracked";
    private static final String MSG_NOT_REGISTERED_TRACKED = "The chat is not registered or the link is not tracked";
    private static final String MSG_CHAT_NOT_REGISTERED = "The chat is not registered yet";

    private static final String DESC_ADD_LINK_ADDED = "You can't add an already added link";
    private static final String DESC_DEL_LINK_UNTRACKED = "You can't delete an untracked link";
    private static final String DESC_GET_LINK_UNTRACKED = "You can't get an untracked link";
    private static final String DESC_LINKS_UNREG = "You can't manipulate with links for unregistered chat";
    private static final String DESC_UPDATE_LINK_UNTRACKED = "You can't update an untracked link";
    private static final String DESC_CHECK_LINK_UNTRACKED = "You can't check an untracked link";

    private final JdbcLinkDao linkDao;
    private final List<Client> availableClients;

    @Override
    public LinkResponse add(Long chatId, URI url) throws BadRequestException, NotFoundException {
        Client client = availableClients
            .stream()
            .filter(c -> c.isLinkSupported(url))
            .findFirst().orElse(null);

        try {
            Link link = linkDao.add(chatId, url);
            if (client != null) {
                client.addLinkData(url, link.getId());
            }

            return new LinkResponse(chatId, link.getUrl());
        } catch (DuplicateKeyException e) {
            throw new BadRequestException(
                MSG_LINK_ALREADY_ADDED,
                DESC_ADD_LINK_ADDED
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_LINKS_UNREG
            );
        }
    }

    @Override
    public LinkResponse removeById(Long linkId) throws NotFoundException {
        try {
            Link link = linkDao.remove(linkId);

            return new LinkResponse(null, link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_DEL_LINK_UNTRACKED
            );
        }
    }

    @Override
    public LinkResponse untrack(Long chatId, URI url) throws NotFoundException {
        try {
            Link link = linkDao.untrack(chatId, url);

            return new LinkResponse(chatId, link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_NOT_REGISTERED_TRACKED,
                DESC_LINKS_UNREG
            );
        }
    }

    @Override
    public ListLinkResponse getAllLinks() {
        List<LinkResponse> linkResponseList = linkDao.findAll()
            .stream()
            .map(link -> new LinkResponse(null, link.getUrl()))
            .toList();

        return new ListLinkResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public LinkResponse getLinkById(Long linkId) throws NotFoundException {
        try {
            Link link = linkDao.findById(linkId);

            return new LinkResponse(null, link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_GET_LINK_UNTRACKED
            );
        }
    }

    @Override
    public ListLinkResponse getLinksByChat(Long chatId) throws NotFoundException {
        try {
            List<LinkResponse> linkResponseList = linkDao.findByChat(chatId)
                .stream()
                .map(link -> new LinkResponse(chatId, link.getUrl()))
                .toList();

            return new ListLinkResponse(linkResponseList, linkResponseList.size());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_CHAT_NOT_REGISTERED,
                DESC_LINKS_UNREG
            );
        }
    }

    @Override
    public Collection<Link> getLongUncheckedLinks(Duration duration) {
        return linkDao.findLongUnchecked(duration);
    }

    @Override
    public LinkResponse setLastUpdateDate(Long linkId, OffsetDateTime updateDate) throws NotFoundException {
        try {
            Link link = linkDao.updateLink(linkId, updateDate);

            return new LinkResponse(null, link.getUrl());
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

            return new LinkResponse(null, link.getUrl());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                MSG_LINK_NOT_TRACKED,
                DESC_CHECK_LINK_UNTRACKED
            );
        }
    }
}
